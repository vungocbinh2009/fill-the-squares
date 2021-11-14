package com.binh.games.fillthesquares.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.View
import android.view.Window
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import com.binh.games.fillthesquares.MainActivity
import com.binh.games.fillthesquares.R
import com.binh.games.fillthesquares.core.basic.BoardCellState
import com.binh.games.fillthesquares.core.basic.GameState
import com.binh.games.fillthesquares.core.basic.MoveDirection
import com.binh.games.fillthesquares.core.basic.getBoardCellState
import com.binh.games.fillthesquares.database.gamedata.GameDataObject
import com.binh.games.fillthesquares.database.gamedata.IGameStatisticDatabase
import com.binh.games.fillthesquares.databinding.ActivityClassicGameBinding
import com.binh.games.fillthesquares.fragment.IBoardFragment
import com.binh.games.fillthesquares.other.InjectConstants
import com.binh.games.fillthesquares.other.OnSwipeTouchListener
import com.binh.games.fillthesquares.viewmodel.ClassicGameViewModel
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
 * Các màn chơi classic sẽ được tổ chức tại đây.
 */
class ClassicGameActivity : AppCompatActivity(), KodeinAware, CoroutineScope {
    companion object {
        const val HOW_TO_PLAY_MESSAGE = "When starting the game, you are on the upper left square. Every time you go through a cell in the table, that cell will turn white. Your task is to switch all the cells in the table to white. On each cell of the table there is a number, which specifies the number of steps you can take when moving on the board. The blue numbers on the board will suggest you this."
    }
    override val coroutineContext: CoroutineContext = Dispatchers.Main
    /**
     * Thuộc tính này quy định màu của các loại ô trong khi hiển thị game
     */
    private val gameColor = mutableMapOf(
            "passedSquare" to Color.TRANSPARENT,
            "currentPosition" to Color.GREEN, // Day chi la gia tri tam thoi, se duoc set lai o onCreate.
            "possibleMove" to Color.BLUE,
            "normalSquare" to Color.GRAY,
            "goldSquare" to Color.YELLOW
    )
    /**
     * Thuộc tính biểu thị kích cỡ bảng.
     */
    private lateinit var boardSize: Pair<Int, Int>
    /**
     * Viewmodel dùng để lấy các thông tin của trò chơi, cần thiết cho việc hiển thị.
     */
    private val viewModel by lazy { ViewModelProvider(this).get(ClassicGameViewModel::class.java) }
    /**
     * Fragment hiển thị bản đồ của trò chơi.
     */
    private var boardFragment: IBoardFragment? = null
    override val kodein: Kodein by closestKodein()
    private var firstInit = true
    private lateinit var binding: ActivityClassicGameBinding

    /**
     * Khởi tạo activity
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Giấu thanh ActionBar.
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()

        // Đọc các giá trị trong Preferences
        gameColor["currentPosition"] = when(PreferenceManager.getDefaultSharedPreferences(this).getString("player_square_color", "green")) {
            "green" -> Color.GREEN
            "blue" -> Color.BLUE
            "red" -> Color.RED
            else -> Color.GREEN
        }

        binding = ActivityClassicGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set dielog cho how to play.
        binding.howToPlayDetailButton.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setMessage(HOW_TO_PLAY_MESSAGE)
                    .setTitle("How to play ?")
                    .setPositiveButton("OK") { _, _ -> }
                    .show()
        }

        // Chuẩn bị màn hình để xuất hiện trước user.
        when(intent.getStringExtra(MainActivity.GAME_MODE)) {
            GameSelectionActivity.CLASSIC_GAME_6X6, GameSelectionActivity.CLASSIC_GAME_6X6_HARD -> {
                boardSize = Pair(6,6)
                boardFragment = kodein.direct.instance(InjectConstants.BOARD_FRAGMENT_6x6)

            }
            GameSelectionActivity.CLASSIC_GAME_8X8, GameSelectionActivity.CLASSIC_GAME_8X8_HARD -> {
                boardSize = Pair(8,8)
                boardFragment = kodein.direct.instance(InjectConstants.BOARD_FRAGMENT_8x8)
            }
        }

        // Add fragment để chơi.
        supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, boardFragment as Fragment)
                .commit()
    }

    /**
     * Những việc cần làm sau khi màn hình chơi game đã xuất hiện.
     */
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            if (firstInit) {
                launch {
                    when (intent.getStringExtra(MainActivity.GAME_MODE)) {
                        GameSelectionActivity.CLASSIC_GAME_6X6 -> {
                            viewModel.build(kodein.direct.instance(InjectConstants.CLASSIC_GAME_MANAGER_6x6))
                        }
                        GameSelectionActivity.CLASSIC_GAME_8X8 -> {
                            viewModel.build(kodein.direct.instance(InjectConstants.CLASSIC_GAME_MANAGER_8x8))
                        }
                        GameSelectionActivity.CLASSIC_GAME_6X6_HARD -> {
                            viewModel.build(kodein.direct.instance(InjectConstants.CLASSIC_GAME_MANAGER_6x6_HARD))
                        }
                        GameSelectionActivity.CLASSIC_GAME_8X8_HARD -> {
                            viewModel.build(kodein.direct.instance(InjectConstants.CLASSIC_GAME_MANAGER_8x8_HARD))
                        }
                    }
                    setupGame()
                    binding.loadingGameTextView.visibility = View.INVISIBLE
                }
                firstInit = false
            }
        }
    }

    /**
     * Thực hiện việc setup game trước khi bắt đầu chơi.
     */
    private fun setupGame() {
        updateScore()
        viewModel.getAllBoardCell().observe(this, {
            it?.forEach {position ->
                setBoardCellColor(position)
                boardFragment?.getSquare(position)?.text = viewModel.getBoardCell(position).number.toString()
            }
        })
        setCurrentPlayerColor()
        updateGameState()
        setupSwipeAction()
    }

    /**
     * Thực hiện việc cập nhất trang thái game trong khi chơi.
     */
    fun updateGame() {
        updateScore()
        removePlayerOldColor()
        updateSquare()
        setCurrentPlayerColor()
        updateGameState()
    }

    /**
     * Cấp nhất điểm số hiện tại.
     */
    private fun updateScore() {
        viewModel.getScore().observe(this, {
            binding.scoreTextView.text = it.toString()
        })
    }

    // Đặt giá trị màu cho các ô trên bảng
    private fun setBoardCellColor(position: Pair<Int, Int>) {
        val view = boardFragment?.getSquare(position)
        when(viewModel.getBoardCell(position).getBoardCellState()) {
            BoardCellState.GOLD -> view?.setBackgroundColor(gameColor.getValue("goldSquare"))
            BoardCellState.PASSED -> view?.setBackgroundColor(gameColor.getValue("passedSquare"))
            BoardCellState.NORMAL -> view?.setBackgroundColor(gameColor.getValue("normalSquare"))
        }
    }

    /**
     * Xóa bỏ các màu sắc cũ của người chơi.
     */
    private fun removePlayerOldColor() {
        viewModel.getPlayerPreviousPosition().observe(this, {
            boardFragment?.getSquare(it!!)?.setBackgroundColor(gameColor.getValue("passedSquare"))
        })
        viewModel.getPlayerPreviousPossibleMove().observe(this, {
            it?.forEach {position ->
                boardFragment?.getSquare(position)?.setTextColor(Color.BLACK)
            }
        })
    }

    /**
     * Cập nhật lại các ô trong bảng (chỉ cập nhật các ô cần thiết.
     */
    private fun updateSquare() {
        viewModel.getChangedBoardCell().observe(this, {
            it?.forEach { position ->
                setBoardCellColor(position)
            }
        })
    }

    /**
     * Setup màu cho các vị trí hiện tại của người chơi, bao gồm cả các bước đi người chơi có thể đi được.
     */
    private fun setCurrentPlayerColor() {
        viewModel.getPlayerCurrentPossibleMove().observe(this, {
            it?.forEach {position ->
                boardFragment?.getSquare(position)?.setTextColor(gameColor.getValue("possibleMove"))
            }
        })
        viewModel.getPlayerCurrentPosition().observe(this, {
            boardFragment?.getSquare(it!!)?.setBackgroundColor(gameColor.getValue("currentPosition"))
            boardFragment?.getSquare(it)?.setTextColor(Color.BLACK)
        })
    }

    /**
     * Cập nhật trạng thái của game.
     */
    private fun updateGameState() {
        viewModel.getGameState().observe(this, Observer {
            when(it) {
                GameState.DONE -> onGameFinished()
                else -> return@Observer
            }
        })
    }

    private var selectGameToPlayLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            when (data?.getStringExtra(GameSelectionActivity.PLAYER_SELECTION)) {
                GameSelectionActivity.CLASSIC_GAME_6X6 -> {
                    val intent = Intent(this, ClassicGameActivity::class.java)
                    intent.putExtra(MainActivity.GAME_MODE, GameSelectionActivity.CLASSIC_GAME_6X6)
                    startActivity(intent)
                }
                "8x8" -> {
                    val intent = Intent(this, ClassicGameActivity::class.java)
                    intent.putExtra(MainActivity.GAME_MODE, GameSelectionActivity.CLASSIC_GAME_8X8)
                    startActivity(intent)
                }
            }
        }
    }

    /**
     * Hàm này sẽ được thực thi sau khi trò chơi kết thúc.
     */
    private fun onGameFinished() {
        val dialog = AlertDialog.Builder(ContextThemeWrapper(this, R.style.AppTheme))
        dialog.setTitle("Done")
                .setMessage("Your score is ${binding.scoreTextView.text}")
                .setPositiveButton("OK") { _ , _ ->

                }
                .show()
        disableSwipeAction()
        binding.howToPlayTextView.visibility = View.INVISIBLE
        binding.scoreTextView.setTextColor(Color.GREEN)
        binding.scoreTextView.setOnClickListener {
            val statisticIntent = Intent(this, GameStatisticActivity::class.java)
            statisticIntent.putExtra(MainActivity.GAME_STATISTIC_MODE,this.intent.getStringExtra(MainActivity.GAME_MODE))
            startActivity(statisticIntent)
        }
        // Xoa bo possible move.
        viewModel.getPlayerCurrentPossibleMove().observe(this, {
            it?.forEach {position ->
                boardFragment?.getSquare(position)?.setTextColor(Color.BLACK)
            }
        })

        // Thêm kết quả chơi vào database
        val database = kodein.direct.instance<IGameStatisticDatabase>(InjectConstants.GAME_STATISTIC_DATABASE)
        database.openDatabase()
        database.addGameData(GameDataObject(
                gameId = UUID.randomUUID().toString(),
                score = viewModel.getScore().value!!,
                gameMode = intent.getStringExtra(MainActivity.GAME_MODE)!!,
                date = Calendar.getInstance().time
        ))

        // Hiển thị các nút.
        binding.newGameButton.visibility = View.VISIBLE
        binding.menuButton.visibility = View.VISIBLE
        binding.newGameButton.setOnClickListener {
            val intent = Intent(this, GameSelectionActivity::class.java)
            selectGameToPlayLauncher.launch(intent)
        }
        binding.menuButton.setOnClickListener {
            backToMainActivity()
        }
    }

    /**
     * Setup cử chỉ swipe của người chơi.
     */
    @SuppressLint("ClickableViewAccessibility")
    private fun setupSwipeAction() {
        binding.classicGameView.setOnTouchListener(object : OnSwipeTouchListener(this) {
            override fun onSwipeLeft() {
                viewModel.move(MoveDirection.LEFT)
                updateGame()
            }

            override fun onSwipeRight() {
                viewModel.move(MoveDirection.RIGHT)
                updateGame()
            }

            override fun onSwipeTop() {
                viewModel.move(MoveDirection.UP)
                updateGame()
            }

            override fun onSwipeBottom() {
                viewModel.move(MoveDirection.DOWN)
                updateGame()
            }
        })
    }

    /**
     * Disable hành động swipe của người chơi.
     */
    @SuppressLint("ClickableViewAccessibility")
    fun disableSwipeAction() {
        binding.classicGameView.setOnTouchListener(object : OnSwipeTouchListener(this) {
            override fun onSwipeLeft() {}
            override fun onSwipeRight() {}
            override fun onSwipeTop() {}
            override fun onSwipeBottom() {}
        })
    }

    /**
     * Quay trở lại màn hình chính.
     */
    private fun backToMainActivity() {
        finish()
    }

    /**
     * Ghi đề nút back để quay lại màn hình chính.
     */
    override fun onBackPressed() {
        val dialog = AlertDialog.Builder(ContextThemeWrapper(this, R.style.AppTheme))
        dialog.setMessage("Quit game ?")
                .setPositiveButton("OK") {_, _ -> backToMainActivity()}
                .setNegativeButton("Cancel") {_, _ -> }
                .show()
    }
}
