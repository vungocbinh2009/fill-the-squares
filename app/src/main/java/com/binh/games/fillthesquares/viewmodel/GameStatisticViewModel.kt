package com.binh.games.fillthesquares.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.binh.games.fillthesquares.database.gamedata.GameDataObject
import com.binh.games.fillthesquares.database.gamedata.IGameStatisticDatabase

/**
 * Đây là class giúp cho các activity, fragment tiếp cận đến các dữ liệu
 * của trò chơi
 * Để sử dụng viewModel này, các activity, fragment cần.
 * 1. gọi phương thức build để tạo ra viewmodel hoàn chỉnh.
 * 2. Mở database để sử dụng bằng phương thức openDatabase.
 * 3. Sử dụng các phương thức cần thiết.
 * 4. Đóng database lại bằng cách gọi closeDatabase.
 */
class GameStatisticViewModel : ViewModel() {
    private lateinit var gameMode: String
    private lateinit var database: IGameStatisticDatabase
    fun build(gameMode: String, database: IGameStatisticDatabase) : GameStatisticViewModel {
        this.gameMode = gameMode
        this.database = database
        return this
    }

    fun openDatabase() : GameStatisticViewModel {
        database.openDatabase()
        return this
    }

    fun getTotalGamePlayed() : LiveData<Long> {
        val result = MutableLiveData<Long>()
        result.value = database.getTotalPlayedGame(gameMode)
        return result
    }

    fun getHighestScore() : LiveData<Int> {
        val result = MutableLiveData<Int>()
        result.value = database.getHighestScore(gameMode)
        return result
    }

    fun getLowestScore() : LiveData<Int> {
        val result = MutableLiveData<Int>()
        result.value = database.getLowestScore(gameMode)
        return result
    }

    fun getAverageScore() : LiveData<Double> {
        val result = MutableLiveData<Double>()
        result.value = database.getAverageScore(gameMode)
        return result
    }

    fun getTopScore(numberOfScore: Int) : LiveData<List<GameDataObject>> {
        val result = MutableLiveData<List<GameDataObject>>()
        result.value = database.getTopScore(numberOfScore, gameMode)
        return result
    }

    fun removeAllGames(vararg gameMode: String) {
        database.removeAllGames(*gameMode)
    }

    fun closeDatabase() {
        database.closeDatabase()
    }
}