package com.binh.games.numberandmaze.activity

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import com.binh.games.numberandmaze.R
import kotlinx.android.synthetic.main.activity_game_selection.*

class GameSelectionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()

        setContentView(R.layout.activity_game_selection)

        button2.setOnClickListener {
            val result = Intent()
            result.putExtra("selection", "6x6")
            setResult(Activity.RESULT_OK, result)
            finish()
        }

        button3.setOnClickListener {
            val result = Intent()
            result.putExtra("selection", "8x8")
            setResult(Activity.RESULT_OK, result)
            finish()
        }
    }

}
