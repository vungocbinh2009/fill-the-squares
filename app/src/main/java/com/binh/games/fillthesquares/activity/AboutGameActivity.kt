package com.binh.games.fillthesquares.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import com.binh.games.fillthesquares.R

class AboutGameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()

        setContentView(R.layout.activity_about_game)
    }
}
