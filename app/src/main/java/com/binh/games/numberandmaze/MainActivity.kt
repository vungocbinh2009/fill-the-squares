package com.binh.games.numberandmaze

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.Toast
import com.binh.games.numberandmaze.activity.ClassicGameActivity
import com.binh.games.numberandmaze.activity.GameSelectionActivity
import com.binh.games.numberandmaze.activity.GameSelectionActivity.Companion.CLASSIC_GAME_6X6
import com.binh.games.numberandmaze.activity.GameSelectionActivity.Companion.CLASSIC_GAME_8X8
import com.binh.games.numberandmaze.activity.GameSelectionActivity.Companion.PLAYER_SELECTION
import com.binh.games.numberandmaze.activity.GameSettingsActivity
import com.binh.games.numberandmaze.activity.GameStatisticActivity
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Lớp này tương ứng với Activity đầu tiên trong ứng dung, khi người chơi
 * vừa mở trò chơi lên.
 */
class MainActivity : AppCompatActivity() {

    companion object {
        /**
         * Đây là giá trị tương ứng khi gửi yêu cầu chọn màn chơi cho
         * GameSelectionActivity.
         */
        const val SELECT_GAME_TO_PLAY = 1

        /**
         * Đây là hằng số tương ứng khi gửi yêu cầu chọn màn chơi để xem
         * thành tích cá nhân, yêu cầu được chuyển cho GameSelectionActivity
         */
        const val SELECT_STATISTIC = 2

        /**
         * Đây là khoảng thời gian người chơi cần đạt được khi bấm nút
         * back 2 lần để thoát game.
         */
        const val TIME_TO_EXIT = 2000

        /**
         * Đây là hằng số đại diện cho kích cỡ của bảng. (6x6, 8x8)
         */
        const val GAME_BOARD_SIZE = "gameBoard"

        /**
         * Đây là hằng số chỉ loại statistic sẽ được hiển thị trên
         * màn hình.
         */
        const val GAME_STATISTIC_MODE = "gameStatistic"
    }
    /**
     * Đây là thời điểm mà người chơi bấm nút back lần thứ nhất
     * để thoát khỏi trò chơi.
     */
    private var backPressOnce: Long = 0

    /**
     * Khởi tạo activity.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()

        setContentView(R.layout.activity_main)

        playButton.setOnClickListener {
            val intent = Intent(this, GameSelectionActivity::class.java)
            startActivityForResult(intent,SELECT_GAME_TO_PLAY)
        }

        statisticButton.setOnClickListener {
            val intent = Intent(this, GameSelectionActivity::class.java)
            startActivityForResult(intent, SELECT_STATISTIC)
        }

        settingsButton.setOnClickListener {
            val intent = Intent(this, GameSettingsActivity::class.java)
            startActivity(intent)
        }
    }

    /**
     * Đây là phương thức dùng để sử lý các kết quả trả về khi bấm nút play.
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            SELECT_GAME_TO_PLAY -> when (resultCode) {
                Activity.RESULT_OK -> when (data?.getStringExtra(PLAYER_SELECTION)) {
                    CLASSIC_GAME_6X6 -> {
                        val intent = Intent(this, ClassicGameActivity::class.java)
                        intent.putExtra(GAME_BOARD_SIZE, CLASSIC_GAME_6X6)
                        startActivity(intent)
                    }
                    "8x8" -> {
                        val intent = Intent(this, ClassicGameActivity::class.java)
                        intent.putExtra(GAME_BOARD_SIZE, CLASSIC_GAME_8X8)
                        startActivity(intent)
                    }
                }
            }
            SELECT_STATISTIC -> when (resultCode) {
                Activity.RESULT_OK -> when (data?.getStringExtra(PLAYER_SELECTION)) {
                    CLASSIC_GAME_6X6 -> {
                        val intent = Intent(this, GameStatisticActivity::class.java)
                        intent.putExtra(GAME_STATISTIC_MODE, CLASSIC_GAME_6X6)
                        startActivity(intent)
                    }
                    CLASSIC_GAME_8X8 -> {
                        val intent = Intent(this, GameStatisticActivity::class.java)
                        intent.putExtra(GAME_STATISTIC_MODE, CLASSIC_GAME_8X8)
                        startActivity(intent)
                    }
                }
            }
        }
    }

    /**
     * Phương thức này ghi đè nút back để thực hiện việc "bấm nút back 2 lần để
     * thoát khỏi trò chơi.
     */
    override fun onBackPressed() {
        if (backPressOnce + TIME_TO_EXIT > System.currentTimeMillis()) {
            super.onBackPressed()
            return
        } else {
            Toast.makeText(this,"Press again to exit", Toast.LENGTH_SHORT).show()
            backPressOnce = System.currentTimeMillis()
        }
    }
}
