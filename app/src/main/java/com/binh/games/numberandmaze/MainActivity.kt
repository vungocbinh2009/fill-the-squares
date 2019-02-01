package com.binh.games.numberandmaze

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.Toast
import com.binh.games.numberandmaze.activity.ClassicGameActivity
import com.binh.games.numberandmaze.activity.GameSelectionActivity
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Lớp này tương ứng với Activity đầu tiên trong ứng dung, khi người chơi
 * vừa mở trò chơi lên.
 */
class MainActivity : AppCompatActivity() {

    /**
     * Đây là giá trị tương ứng khi gửi yêu cầu chọn màn chơi cho
     * GameSelectionActivity.
     */
    @Suppress("PrivatePropertyName")
    private val SELECT_GAME_TO_PLAY = 1

    /**
     * Đây là khoảng thời gian người chơi cần đạt được khi bấm nút
     * back 2 lần để thoát game.
     */
    @Suppress("PrivatePropertyName")
    private val TIME_TO_EXIT = 2000

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
    }

    /**
     * Đây là phương thức dùng để sử lý các kết quả trả về khi bấm nút play.
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            SELECT_GAME_TO_PLAY -> when (resultCode) {
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
