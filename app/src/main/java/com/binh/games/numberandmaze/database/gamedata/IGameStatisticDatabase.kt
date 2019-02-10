package com.binh.games.numberandmaze.database.gamedata

/**
 * Đây là interface cơ bản nhất, dùng để thao tác trực tiếp với
 * database nhằm lấy được các kết quả cần thiết.
 * Các lớp khác nếu cần đến dữ liệu về thành tích trong game thì
 * dùng interface này để lấy các kết quả cần thiết.
 * Các hàm trong interface này sẽ được bổ sung thường xuyên cho phù hợp
 * với nhu cầu sử dụng.
 * Lưu ý là khi sử dụng, ta phải gọi openDatabase trước khi dùng và closeDatabase
 * sau khi sử dụng, và nên thực hiện chúng tại backgroundThread (dùng doAsync) của
 * Anko là hợp lý nhất.
 */
interface IGameStatisticDatabase {
    fun openDatabase() : IGameStatisticDatabase

    fun getTotalPlayedGame(gameMode: String) : Long

    fun getTotalPlayedGameWon(gameMode: String) : Long

    fun getTotalPlayedGameInAllMode() : Long

    fun getTotalPlayedGameWonInAllMode() : Long

    fun getHighestScore(gameMode: String) : Int?

    fun getLowestScore(gameMode: String) : Int?

    fun getAverageScore(gameMode: String) : Double

    fun getTopScore(numberOfGame: Int, gameMode: String): List<GameDataObject>

    fun addGameData(data: GameDataObject) : IGameStatisticDatabase

    fun removeAllGames(vararg gameMode: String) : IGameStatisticDatabase

    fun findGameById(id: String) : GameDataObject?

    fun closeDatabase()
}