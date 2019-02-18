package com.binh.games.numberandmaze.activity

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.Window
import com.binh.games.numberandmaze.MainActivity
import com.binh.games.numberandmaze.R
import kotlinx.android.synthetic.main.activity_game_selection.*

/**
 * Đây là activity dùng để giúp người chơi chọn
 * màn chơi mà họ muốn chơi.
 */
class GameSelectionActivity : AppCompatActivity() {
    /**
     * Đây là danh sách các hằng số của lớp này.
     * Nó định nghĩa 1 cái tên thóng nhất cho các màn chơi,
     * có hiệu lực cho toàn bộ trò chơi.
     */
    companion object {
        const val PLAYER_SELECTION = "selection"
        const val CLASSIC_GAME_6X6 = "6x6"
        const val CLASSIC_GAME_8X8 = "8x8"
    }
    /**
     * Thực hiện khởi tạo giao diện và xử lý các sự kiện
     * khi người chơi bấm vào các màn chơi.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()

        setContentView(R.layout.activity_game_selection)

        classicGame6x6Button.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra(PLAYER_SELECTION, CLASSIC_GAME_6X6)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }

        classicGame8x8Button.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra(PLAYER_SELECTION, CLASSIC_GAME_8X8)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }

    /**
     * Hàm này dùng để hiển thị snackbar chờ đợi cho người dùng.
     */
    private fun showSnackBar() {
        Snackbar.make(gameSelectionView, "Loading, please wait", Snackbar.LENGTH_INDEFINITE).show()
    }

    override fun onBackPressed() {
        // Quay trở lại menu.
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }
}
