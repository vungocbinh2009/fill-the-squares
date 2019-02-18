package com.binh.games.fillthesquares.fragment

import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import java.util.concurrent.TimeUnit

/**
 * Đây là 1 interface dùng để cung cấp các chức năng cơ bản
 * cho 1 bảng trong trò chơi.
 *
 * Để implement interface này, chỉ cần override thuộc tính table
 * ứng với bảng trong fragment là được.
 */
interface IBoardFragment {

    /**
     * Thuộc tính này ứng với 1 bảng trong fragment.
     */
    val table: TableLayout

    /**
     * Phương thức này giúp ta tạo ra animation di chuyển của người chơi
     * 1 cách dễ dàng (timeDelay là thời gian delay - tính bằng miliseconds)
     */
    fun moving(step: List<Pair<Int, Int>>, timeDelay: Int, colorResId: Int) {
        for (position in step) {
            setSquareColor(position, colorResId)
            TimeUnit.MILLISECONDS.sleep(timeDelay.toLong())
        }
    }

    /**
     * Phương thức này set màu cho 1 ô nhất định trong fragment.
     */
    fun setSquareColor(position: Pair<Int, Int>, colorResId: Int) {
        val square = getSquare(position)
        square.setBackgroundResource(colorResId)
    }

    /**
     * Phương thức này đặt lại giá trị trong 1 ô cho trước.
     */
    fun setNumber(position: Pair<Int, Int>, number: Int) {
        val square = getSquare(position)
        square.text = number.toString()
    }

    /**
     * Phương thức này trả về view tương ứng với 1 ô cho trước
     * trong bảng.
     */
    fun getSquare(position: Pair<Int, Int>) : TextView {
        val tableRow = table.getChildAt(position.first) as TableRow
        return tableRow.getChildAt(position.second) as TextView
    }
}