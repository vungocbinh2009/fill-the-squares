package com.binh.games.numberandmaze.activity

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.Window
import android.view.WindowManager
import com.binh.games.numberandmaze.MainActivity
import com.binh.games.numberandmaze.R
import com.binh.games.numberandmaze.core.basic.IGameManager
import com.binh.games.numberandmaze.database.gamedata.GameDataObject
import com.binh.games.numberandmaze.database.gamedata.IGameStatisticDatabase
import com.binh.games.numberandmaze.fragment.IBoardFragment
import com.binh.games.numberandmaze.other.InjectConstant
import com.binh.games.numberandmaze.other.OnSwipeTouchListener
import com.binh.games.numberandmaze.viewmodel.ClassicGameViewModel
import kotlinx.android.synthetic.main.activity_classic_game.*
import kotlinx.coroutines.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.direct
import org.kodein.di.generic.instance
import java.util.*
import kotlin.coroutines.CoroutineContext

/**
 * Đây là activity dùng để chơi game.
 *
 * Các màn chơi classic sẽ được tổ chức tại đây.
 */
class ClassicGameActivity : AppCompatActivity(), KodeinAware, CoroutineScope {
    override val coroutineContext: CoroutineContext = Dispatchers.Main
    /**
     * Thuộc tính này là mã số của hành động "chọn game mới"
     *
     * Nó thường được diễn ra sau khi người chơi đã chơi xong màn chơi này.
     */
    companion object {
        const val SELECT_NEW_GAME = 1
        val SQUARE_COLOR_LIST: Map<String, Int> = mapOf("green" to R.color.appNameColor, "purple" to R.color.colorPrimaryDark, "red" to R.color.squareColorRed)
    }
    private var playerName: String? = "player1"
    /**
     * Thuộc tính này lưu lại màu của ô dành cho người chơi.
     */
    private var playerColor: Int = R.color.appNameColor

    /**
     * Thuộc tính này lưu lại màu của ô đich (là ô mà người chơi
     * cần đến được sau 1 số hữu hạn bước.
     */
    private var wonSquareColor: Int = R.color.squareColorBlue

    override val kodein: Kodein by closestKodein()

    /**
     * Thuộc tính này lưu lại kích thước của bảng trong trò chơi.
     */
    private lateinit var boardSize: Pair<Int, Int>

    /**
     * Thuộc tính này lưu lại 1 viewModel, nơi sẽ cung cấp các thông tin
     * về màn chơi, và các thạng thái hiện tại của nó.
     */
    private lateinit var gameViewModel: ClassicGameViewModel

    /**
     * Thuộc tính này lưu lại bảng (là 1 fragment) của trò chơi.
     */
    private lateinit var boardFragment: IBoardFragment

    private var firstInit = true

    /**
     * Phương thức onCreate, nó sẽ được gọi khi activity được khởi tạo.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Giấu thanh ActionBar.
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()

        setContentView(R.layout.activity_classic_game)

        val boardFragment =
            when (intent.getStringExtra("gameBoard")) {
                "6x6" -> {
                    boardSize = Pair(6, 6)
                    kodein.direct.instance(InjectConstant.BOARD_6X6_FRAGMENT)
                }
                "8x8" -> {
                    boardSize = Pair(8, 8)
                    kodein.direct.instance(InjectConstant.BOARD_8X8_FRAGMENT)
                }
                else -> kodein.direct.instance<IBoardFragment>(InjectConstant.BOARD_8X8_FRAGMENT)
            }
        supportFragmentManager.beginTransaction().add(R.id.fragment_container,
                    boardFragment as Fragment).commit()
        this.boardFragment = boardFragment

        playerColor = SQUARE_COLOR_LIST[PreferenceManager.getDefaultSharedPreferences(this)?.getString("player_color", "green")]!!
        playerName = PreferenceManager.getDefaultSharedPreferences(this).getString("player_name", "player1")
        if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean("is_full_screen", false)) {
            window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }

    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if(hasFocus) {
            if (firstInit) {
                launch {
                    val gameViewModel = ViewModelProviders.of(this@ClassicGameActivity)[ClassicGameViewModel::class.java]
                    when (intent.getStringExtra("gameBoard")) {
                        "6x6" -> {
                            gameViewModel.build(kodein.direct
                                    .instance(InjectConstant.CLASSIC_GAME_MANAGER_6X6_WITH_VALIDATION))
                        }
                        "8x8" -> {
                            gameViewModel.build(kodein.direct
                                    .instance(InjectConstant.CLASSIC_GAME_MANAGER_8X8_WITH_VALIDATION))
                        }
                    }
                    firstInit = false
                    this@ClassicGameActivity.gameViewModel = gameViewModel
                    setupGame()
                    loadingGameTextView.visibility = View.INVISIBLE
                }
            }
        }
    }

    private fun setupGame() {
        updateNumber()
        updateScore()
        setCurrentColor()
        setSwipeAction()
    }

    /**
     * Hàm này cập nhật các con số của các ô trong bảng.
     */
    private fun updateNumber() {
        for (i in 0..(boardSize.first - 1)) {
            for (j in 0..(boardSize.second - 1)) {
                gameViewModel.boardNumber(Pair(i, j)).observe(this, Observer {
                    boardFragment.setNumber(Pair(i,j), it!!)
                })
            }
        }
    }

    /**
     * Hàm này tô màu các ô trên bảng, tương ứng với trạng thái hiện tại của trò chơi.
     */
    private fun setCurrentColor() {
        // Tô màu cho các ô người chơi có thể đi được.
        gameViewModel.playerPossibleMove().observe(this, Observer {
            for (possibleSquare in it!!) {
                boardFragment.setSquareColor(possibleSquare, R.color.possibleMoveColor)
            }
        })

        // Tô màu ô của người chơi.
        gameViewModel.playerPosition().observe(this, Observer {
            boardFragment.setSquareColor(it!!, playerColor)
        })
        // Tô màu ô đích.
        gameViewModel.wonPosition().observe(this, Observer {
            for (wonSquare in it!!) {
                boardFragment.setSquareColor(wonSquare, wonSquareColor)
            }
        })
    }

    /**
     * Hàm này tạo ra các action swipe, giúp người chơi thao tác với bảng của trò chơi.
     */
    private fun setSwipeAction() {
        classicGameView.setOnTouchListener(object: OnSwipeTouchListener(this@ClassicGameActivity) {
            override fun onSwipeLeft() {
                gameViewModel.left()
                updateGame()
            }

            override fun onSwipeRight() {
                gameViewModel.right()
                updateGame()
            }

            override fun onSwipeTop() {
                gameViewModel.up()
                updateGame()
            }

            override fun onSwipeBottom() {
                gameViewModel.down()
                updateGame()
            }
        })
    }

    /**
     * Hàm này gọi các hàm khác, cần thiết cho việc cập nhật lại trạng thái
     * của bảng sau khi người chơi di chuyển.
     */
    private fun updateGame() {
        updateScore()
        removeOldColor()
        setCurrentColor()
        checkGameState()
    }

    /**
     * Hàm này cập nhật lại điểm số hiện tại của người chơi.
     */
    private fun updateScore() {
        gameViewModel.score().observe(this, Observer {
            scoreTextView.text = it.toString()
        })
    }

    /**
     * Hàm này xóa bỏ màu của ô ở vị trí trước đó của người chơi.
     */
    private fun removeOldColor() {
        if (gameViewModel.playerPosition().value != gameViewModel.previousPlayerPosition().value) {
            gameViewModel.previousPlayerPosition().observe(this, Observer {
                boardFragment.setSquareColor(it!!, R.color.squareDefaultColor)
            })
        }

        gameViewModel.previousPlayerPossibleMove().observe(this, Observer {
            for (oldPossibleMove in it!!) {
                boardFragment.setSquareColor(oldPossibleMove, R.color.squareDefaultColor)
            }
        })
    }

    /**
     * Hàm này kiểm tra trạng thái của trò chơi (thắng / thua) ở thời điểm
     * hiện tại và gọi các hàm cần thiết, nếu có.
     */
    private fun checkGameState() {
        when(gameViewModel.gameState().value) {
            IGameManager.GameState.PLAYING -> return
            else -> onGameFinished()
        }
    }

    /**
     * Hàm này dược gọi khi trò chơi đã kết thúc.
     * Nó sẽ thông báo cho người chơi điểm số, và tùy chọn
     * chơi ván mới hoặc quay trỏ lại màn hình ban đầu.
     */
    private fun onGameFinished() {
        val database = kodein.direct.instance<IGameStatisticDatabase>(InjectConstant.GAME_STATISTIC)
        database.openDatabase()
        database.addGameData(GameDataObject(
                gameId = UUID.randomUUID().toString(),
                playerName = "player1",
                score = gameViewModel.score().value!!,
                gameMode = intent.getStringExtra(MainActivity.GAME_BOARD_SIZE),
                date = Calendar.getInstance().time))
        database.closeDatabase()

        howToPlayTextView.visibility = View.INVISIBLE
        newGameButton.visibility = View.VISIBLE
        menuButton.visibility = View.VISIBLE
        scoreTextView.setBackgroundResource(R.color.appNameColor)

        //Disable swipe
        classicGameView.setOnTouchListener(object: OnSwipeTouchListener(this@ClassicGameActivity) {
            override fun onSwipeLeft() {}
            override fun onSwipeRight() {}
            override fun onSwipeTop() {}
            override fun onSwipeBottom() {}
        })

        newGameButton.setOnClickListener {
            val intent = Intent(this@ClassicGameActivity, GameSelectionActivity::class.java)
            startActivityForResult(intent, SELECT_NEW_GAME)
        }

        menuButton.setOnClickListener {
            backToMainActivity()
        }
    }

    /**
     * Hàm này dùng để xử lí kết quả thu được sau khi người chơi click chọn 1 màn chơi mới.
     * (Sau khi trò chơi đã kết thúc.
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            SELECT_NEW_GAME -> when (resultCode) {
                Activity.RESULT_OK -> when (data?.getStringExtra("selection")) {
                    "6x6" -> {
                        val intent = Intent(this, ClassicGameActivity::class.java)
                        intent.putExtra("gameBoard", "6x6")
                        startActivity(intent)
                    }
                    "8x8" -> {
                        val intent = Intent(this, ClassicGameActivity::class.java)
                        intent.putExtra("gameBoard", "8x8")
                        startActivity(intent)
                    }
                }
            }
        }
    }

    /**
     * Hàm này dùng để quay trở lại activity ban đầu.
     */
    private fun backToMainActivity() {
        val intent = Intent(this@ClassicGameActivity, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

    /**
     * Ghi đè nút back trên điện thoại người chơi.
     */
    override fun onBackPressed() {
        backToMainActivity()
    }
}
