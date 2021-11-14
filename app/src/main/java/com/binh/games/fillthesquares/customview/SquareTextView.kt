package com.binh.games.fillthesquares.customview

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import androidx.appcompat.widget.AppCompatTextView
import kotlin.math.min

/**
 * Lớp này biểu diễn các ô vuông trong bản đồ của trò chơi.
 */
class SquareTextView(context: Context?, attrs: AttributeSet?) : AppCompatTextView(context!!, attrs) {

    /**
     * Phương thức này tính lại kích thước của text view để đảm bảo
     * rằng nó có hình vuông.
     */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)
        val size = min(width, height)
        setMeasuredDimension(size, size)

        // Một số cài đặt khác.
        gravity = Gravity.CENTER
    }
}