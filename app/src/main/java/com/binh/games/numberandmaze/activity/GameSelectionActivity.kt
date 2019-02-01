package com.binh.games.numberandmaze.activity

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.Window
import com.binh.games.numberandmaze.R
import kotlinx.android.synthetic.main.activity_game_selection.*

/**
 * Đây là activity dùng để giúp người chơi chọn
 * màn chơi mà họ muốn chơi.
 */
class GameSelectionActivity : AppCompatActivity() {

    /**
     * Thực hiện khởi tạo giao diện và xử lý các sự kiện
     * khi người chơi bấm vào các màn chơi.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()

        setContentView(R.layout.activity_game_selection)

        classic6x6button.setOnTouchListener { _, event ->
            showSnackBar()
            val result = Intent()
            result.putExtra("selection", "6x6")
            setResult(Activity.RESULT_OK, result)
            finish()
            onTouchEvent(event)
        }

        classic8x8button.setOnTouchListener { _, event ->
            showSnackBar()
            val result = Intent()
            result.putExtra("selection", "8x8")
            setResult(Activity.RESULT_OK, result)
            finish()
            onTouchEvent(event)
        }
        /*
        clasic6x6button.setOnClickListener {
            showSnackBar()
            val result = Intent()
            result.putExtra("selection", "6x6")
            setResult(Activity.RESULT_OK, result)
            finish()
        }

        classic8x8button.setOnClickListener {
            showSnackBar()
            val result = Intent()
            result.putExtra("selection", "8x8")
            setResult(Activity.RESULT_OK, result)
            finish()
        }
        */
    }

    private fun showSnackBar() {
        Snackbar.make(gameSelectionView, "Loading, please wait", Snackbar.LENGTH_INDEFINITE).show()
    }

}
