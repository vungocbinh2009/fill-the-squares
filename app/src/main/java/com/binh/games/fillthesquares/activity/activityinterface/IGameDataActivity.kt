package com.binh.games.fillthesquares.activity.activityinterface

import com.binh.games.fillthesquares.core.basic.IGameManager

/**
 * Đây là interface dùng để giúp các item lấy được các thông tin cần thiết
 * từ activity. Các activity sẽ implement interface này.
 */
interface IGameDataActivity {
    fun getOneSquarePosition(message: String) : Pair<Int, Int>

    fun getOneNumber(message: String) : Int

    fun getGameManager() : IGameManager

    fun updateGameScreen()
}