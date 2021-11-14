package com.binh.games.fillthesquares.activity

import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.binh.games.fillthesquares.databinding.ActivityAboutGameBinding

/**
 * Đây là class chứa layout hiển thị thông tin về trò chơi.
 */
class AboutGameActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAboutGameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()

        binding = ActivityAboutGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
