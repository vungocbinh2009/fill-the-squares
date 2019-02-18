@file:Suppress("LocalVariableName")

package com.binh.games.fillthesquares.other

import android.content.Context
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
/**
 * Class này được dùng để implement hành động swipe của người dùng trong trò chơi.
 */
abstract class OnSwipeTouchListener(context: Context) : View.OnTouchListener {
    private val gestureDetector: GestureDetector = GestureDetector(context, GestureListener())

    /**
     * swipe sang trái.
     */
    abstract fun onSwipeLeft()

    /**
     * swipe sang phải.
     */
    abstract fun onSwipeRight()

    /**
     * swipe lên trên.
     */
    abstract fun onSwipeTop()

    /**
     * swipe xuống dưới.
     */
    abstract fun onSwipeBottom()

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        return gestureDetector.onTouchEvent(event)
    }

    private inner class GestureListener : GestureDetector.SimpleOnGestureListener() {
        override fun onFling(e1: MotionEvent, e2: MotionEvent, velocityX: Float,
                             velocityY: Float): Boolean {
            val SWIPE_DISTANCE_THRESHOLD_X = 100 // Distance: Khoảng cách.
            val SWIPE_VELOCITY_THRESHOLD_X = 100 // Velocity: Tốc độ.
            val SWIPE_DISTANCE_THRESHOLD_Y = 100
            val SWIPE_VELOCITY_THRESHOLD_Y = 100

            val distanceX = e2.x - e1.x
            val distanceY = e2.y - e1.y
            if (Math.abs(distanceX) > Math.abs(distanceY) && Math.abs(distanceX) > SWIPE_DISTANCE_THRESHOLD_X && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD_X) {
                if (distanceX > 0)
                    onSwipeRight()
                else
                    onSwipeLeft()
                return true
            }

            if (Math.abs(distanceX) < Math.abs(distanceY) && Math.abs(distanceY) > SWIPE_DISTANCE_THRESHOLD_Y
                    && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD_Y) {
                if (distanceY < 0)
                    onSwipeTop()
                else
                    onSwipeBottom()
                return true
            }
            return false
        }
    }
}