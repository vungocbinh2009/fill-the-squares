package com.binh.games.fillthesquares.core.basic

/**
 * Enum này định nghĩa các hướng di chuyển của người chơi trong game.
 */
enum class MoveDirection {UP, DOWN, LEFT, RIGHT}
interface IBoard {
    val size: Pair<Int, Int>

    /**
     * Trả về ô tiếp theo trong bảng theo hướng di chuyển.
     */
    fun nextCell(direction: MoveDirection, currentPosition: Pair<Int, Int>) : Pair<Int, Int>

    /**
     * Đặt trạng thái cho 1 hoặc nhiều ô trong bảng.
     */
    fun setBoardCellState(state: BoardCellState,vararg positions: Pair<Int, Int>)

    /**
     * Đặt giá trị cho 1 hoặc nhiều ô trong bảng.
     */
    fun setBoardCellNumber(number: Int, vararg positions: Pair<Int, Int>)

    /**
     * Lấy 1 hoặc nhiều đối tượng boardCell khi biết vị trí của nó trong bảng.
     */
    fun getBoardCell(vararg positions: Pair<Int, Int>) : List<BoardCell>

    /**
     * Lấy về 1 danh sách các ô trong bảng dựa vào trạng thái.
     */
    fun getAllBoardCellPosition(vararg state: BoardCellState) : List<Pair<Int, Int>>

    /**
     * Cập nhật boardCell dựa vào nước đi của người chơi.
     */
    fun updateBoardCell(possibleChangeBoardCell: List<Pair<Int, Int>>)

    /**
     * Lấy về danh sách các ô trong bảng có sự thay đổi sau 1 nước đi của người chơi.
     */
    fun getChangedBoardCellPosition() : List<Pair<Int, Int>>

    /**
     * Trả về trạng thái của game hiện tại.
     */
    fun getGameState() : GameState
}