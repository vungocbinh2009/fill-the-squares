package com.binh.games.fillthesquares.gamespecialitem

import com.binh.games.fillthesquares.activity.activityinterface.IGameDataActivity

/**
 * Đây là interface cơ bản nhất trong module này.
 * Nó định nghĩa phương thức cơ bản nhất mà mọi special item phải có.
 * Đó là phương thức applyItem, nó sẽ được gọi khi 1 activity muốn apply
 * 1 item nào đó trong khi chơi.
 */
interface Item {
    fun applyItem(activity: IGameDataActivity)
}