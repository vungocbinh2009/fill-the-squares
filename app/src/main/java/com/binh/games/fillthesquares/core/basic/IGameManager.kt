package com.binh.games.fillthesquares.core.basic

/**
 * Interface mô tả 1 đối tượng trong ứng dụng, dùng để quản lý
 * các đối tượng xuất hiện trong trò chơi.
 */
interface IGameManager {
    /**
     * enum này đại diện cho các trạng thái của trò chơi (thắng / thua / đang chơi).
     */
    enum class GameState {WON, LOST, PLAYING}

    /**
     * Thuộc tính này tương ứng với bảng trong màn chơi.
     * Bạn có thể lấy các thông tin về bảng từ thuộc tính này.
     */
    val board: IBoard

    /**
     * Thuộc tính này tương ứng với người chơi trong màn chơi.
     * Bạn có thể lấy các thông tin về người chơi thông qua thuộc tính này.
     */
    var player: IPlayer

    /**
     * Thuộc tính này tương ứng với trạng thái của trò chơi hiện tại.
     * Bạn có thể sử dụng chúng khi cần thiết.
     */
    val gameState: GameState

    /**
     * Thuộc tính này lưu lại số điểm mà người chơi đang có hiện tại.
     */
    var score: Int

    /**
     * Phương thúc này được gọi khi các module cấp cao hơn muốn điều khiển
     * người chơi đi lên.
     */
    fun up() : IGameManager

    /**
     * Phương thúc này được gọi khi các module cấp cao hơn muốn điều khiển
     * người chơi đi xuống.
     */
    fun down() : IGameManager

    /**
     * Phương thúc này được gọi khi các module cấp cao hơn muốn điều khiển
     * người chơi đi sang phải.
     */
    fun left() : IGameManager

    /**
     * Phương thúc này được gọi khi các module cấp cao hơn muốn điều khiển
     * người chơi đi sang trái.
     */
    fun right() : IGameManager

    /**
     * Phương thức này dùng để lấy danh sách các vị trí người chơi có thể
     * đi được tại thời điểm hiện tại.
     */
    fun getPossibleMove() : List<Pair<Int, Int>> {
        return player.playerPossibleMove(player.playerPosition, board)
    }

    /**
     * Phương thức này dùng để lấy danh sách các vị trí người chơi có thể
     * đi được tại thời điểm trước đó.
     */
    fun getPreviousPossibleMove() : List<Pair<Int, Int>> {
        return player.playerPossibleMove(player.previousPlayerPosition, board)
    }
}