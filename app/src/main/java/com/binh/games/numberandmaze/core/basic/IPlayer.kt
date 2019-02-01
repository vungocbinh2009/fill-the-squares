package com.binh.games.numberandmaze.core.basic

/**
 * Interface này mô tả 1 người chơi trong trò chơi.
 * Các phương thức dưới đây giúp người chơi có thể di chuyển trên bản đồ.
 * đồng thời cung cấp các thống tin về vị trí của người chơi
 *  - Vị trí hiện tại.
 *  - Lịch sử về các vị trí đã di chuyển đến.
 *  - Các ô mà người chơi đã di chuyển qua (trong 1 lần di chuyển gần nhất)
 */
interface IPlayer {
    /**
     * Thuộc tính này lưu lại vị trí của người chơi hiện tai.
     */
    var playerPosition: Pair<Int, Int>

    /**
     * Phương thức này lưu lại vj trí của người chơi trước đó.
     */
    var previousPlayerPosition: Pair<Int, Int>

    /**
     * Phương thúc này trả về danh sách các vị trí mà người chơi đã đi đến.
     */
    fun playerPositionHistory() : List<Pair<Int, Int>>

    /**
     * Phương thức trả về các ô mà người chơi đã đi qua trong nước đi trước đó.
     */
    fun playerMoveAllCell() : List<Pair<Int, Int>>

    /**
     * Phương thức này giúp người chơi đi lên trên.
     */
    fun up(board: IBoard) : Pair<Int, Int>

    /**
     * Phương thức này giúp người chơi đi xuống dưới.
     */
    fun down(board: IBoard) : Pair<Int, Int>

    /**
     * Phương thức này giúp người chơi đi sang trái.
     */
    fun left(board: IBoard) : Pair<Int, Int>

    /**
     * Phương thức này giúp người chơi đi sang phải.
     */
    fun right(board: IBoard) : Pair<Int, Int>
}