package com.binh.games.fillthesquares

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Window
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.binh.games.fillthesquares.activity.ClassicGameActivity
import com.binh.games.fillthesquares.activity.GameSelectionActivity
import com.binh.games.fillthesquares.activity.GameSelectionActivity.Companion.CLASSIC_GAME_6X6
import com.binh.games.fillthesquares.activity.GameSelectionActivity.Companion.CLASSIC_GAME_6X6_HARD
import com.binh.games.fillthesquares.activity.GameSelectionActivity.Companion.CLASSIC_GAME_8X8
import com.binh.games.fillthesquares.activity.GameSelectionActivity.Companion.CLASSIC_GAME_8X8_HARD
import com.binh.games.fillthesquares.activity.GameSelectionActivity.Companion.PLAYER_SELECTION
import com.binh.games.fillthesquares.activity.GameSettingsActivity
import com.binh.games.fillthesquares.activity.GameStatisticActivity
import com.binh.games.fillthesquares.databinding.ActivityMainBinding

/**
 * Lớp này tương ứng với Activity đầu tiên trong ứng dung, khi người chơi
 * vừa mở trò chơi lên.
 */
class MainActivity : AppCompatActivity() {

    companion object {
        /**
         * Đây là khoảng thời gian người chơi cần đạt được khi bấm nút
         * back 2 lần để thoát game.
         */
        const val TIME_TO_EXIT = 2000

        /**
         * Đây là hằng số đại diện cho kích cỡ của bảng. (6x6, 8x8)
         */
        const val GAME_MODE = "gameMode"

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

    private var selectGameLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        val data = result?.data
        when (data?.getStringExtra(PLAYER_SELECTION)) {
            CLASSIC_GAME_6X6 -> {
                val intent = Intent(this, ClassicGameActivity::class.java)
                intent.putExtra(GAME_MODE, CLASSIC_GAME_6X6)
                startActivity(intent)
            }
            CLASSIC_GAME_8X8 -> {
                val intent = Intent(this, ClassicGameActivity::class.java)
                intent.putExtra(GAME_MODE, CLASSIC_GAME_8X8)
                startActivity(intent)
            }
            CLASSIC_GAME_6X6_HARD -> {
                val intent = Intent(this, ClassicGameActivity::class.java)
                intent.putExtra(GAME_MODE, CLASSIC_GAME_6X6_HARD)
                startActivity(intent)
            }
            CLASSIC_GAME_8X8_HARD -> {
                val intent = Intent(this, ClassicGameActivity::class.java)
                intent.putExtra(GAME_MODE, CLASSIC_GAME_8X8_HARD)
                startActivity(intent)
            }
        }
    }

    private lateinit var binding: ActivityMainBinding
    /**
     * Khởi tạo activity.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.playButton.setOnClickListener {
            val intent = Intent(this, GameSelectionActivity::class.java)
            selectGameLauncher.launch(intent)
        }

        binding.settingsButton.setOnClickListener {
            val intent = Intent(this, GameSettingsActivity::class.java)
            startActivity(intent)
        }

        binding.privacyPolicyButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://sites.google.com/view/vbinhdev98ssitesprivacypolicy"))
            startActivity(intent)
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
