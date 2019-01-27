package com.binh.games.numberandmaze.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import com.binh.games.numberandmaze.R
import com.binh.games.numberandmaze.fragment.Board6x6Fragment
import com.binh.games.numberandmaze.fragment.Board8x8Fragment

class ClassicGameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()

        setContentView(R.layout.activity_classic_game)

        val gameBoard = intent.getStringExtra("gameBoard")
        when(gameBoard) {
            "6x6" -> {
                supportFragmentManager.beginTransaction()
                        .add(R.id.fragment_container, Board6x6Fragment()).commit()
            }
            "8x8" -> {
                supportFragmentManager.beginTransaction()
                        .add(R.id.fragment_container, Board8x8Fragment()).commit()
            }
        }
    }
}
