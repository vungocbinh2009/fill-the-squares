package com.binh.games.fillthesquares.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.binh.games.fillthesquares.MainActivity
import com.binh.games.fillthesquares.databinding.ActivityGameSelectionBinding

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
        const val CLASSIC_GAME_6X6_HARD = "6x6Hard"
        const val CLASSIC_GAME_8X8_HARD = "8x8Hard"
    }

    private lateinit var binding: ActivityGameSelectionBinding
    /**
     * Thực hiện khởi tạo giao diện và xử lý các sự kiện
     * khi người chơi bấm vào các màn chơi.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()

        binding = ActivityGameSelectionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.classicGame6x6Button.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra(PLAYER_SELECTION, CLASSIC_GAME_6X6)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }

        binding.classicGame8x8Button.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra(PLAYER_SELECTION, CLASSIC_GAME_8X8)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }

        binding.classicGame6x6HardButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra(PLAYER_SELECTION, CLASSIC_GAME_6X6_HARD)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }

        binding.classicGame8x8HardButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra(PLAYER_SELECTION, CLASSIC_GAME_8X8_HARD)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }

    override fun onBackPressed() {
        // Quay trở lại menu.
        finish()
    }
}
