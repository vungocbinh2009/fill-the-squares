package com.binh.games.numberandmaze

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import com.binh.games.numberandmaze.activity.ClassicGameActivity
import com.binh.games.numberandmaze.activity.GameSelectionActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    @Suppress("PrivatePropertyName")
    private val SELECT_GAME_TO_PLAY = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()

        setContentView(R.layout.activity_main)

        button.setOnClickListener {
            val intent = Intent(this, GameSelectionActivity::class.java)
            startActivityForResult(intent,SELECT_GAME_TO_PLAY)
        }
    }

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
}
