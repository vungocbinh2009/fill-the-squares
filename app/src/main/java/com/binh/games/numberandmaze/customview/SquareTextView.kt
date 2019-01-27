package com.binh.games.numberandmaze.customview

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.widget.TextView

class SquareTextView(context: Context?, attrs: AttributeSet?) : TextView(context, attrs) {
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)
        val size = Math.min(width, height)
        setMeasuredDimension(size, size)

        // Một số cài đặt khác.
        gravity = Gravity.CENTER
    }
}