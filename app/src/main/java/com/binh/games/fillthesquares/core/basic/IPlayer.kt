package com.binh.games.fillthesquares.core.basic

interface IPlayer {
    /**
     * Người chơi di chuyển.
     */
    fun move(direction: MoveDirection, board: IBoard) : Boolean

    /**
     * Trả về vị trí hiện tại.
     */
    fun getCurrentPosition() : Pair<Int, Int>

    /**
     * Trả về vị trí trước đó.
     */
    fun getPreviousPosition() : Pair<Int, Int>

    /**
     * Trả về các nước đi có thể có tiếp theo.
     */
    fun getPossibleMove(board: IBoard) : List<Pair<Int, Int>>

    /**
     * Trả về các nước đi có thể có trước đó.
     */
    fun getPreviousPossibleMove(board: IBoard) : List<Pair<Int, Int>>

    /**
     * Trả về danh sách các ô có thể sẽ thay đổi trên bảng.
     */
    fun getPossibleChangeBoardCell() : List<Pair<Int, Int>>

    /**
     * Trả về điểm số cộng thêm cho bước đi gần nhất.
     */
    fun moveScore() : Int
}