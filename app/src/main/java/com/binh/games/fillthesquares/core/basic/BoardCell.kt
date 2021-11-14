package com.binh.games.fillthesquares.core.basic

/**
 * Đây là sealed class biểu diễn các loại boardCell trong trò chơi.
 */
sealed class BoardCell(var number: Int) {
    class NormalBoardCell(number: Int) : BoardCell(number)
    class PassedBoardCell(number: Int) : BoardCell(number)
    class GoldBoardCell(number: Int) : BoardCell(number)
}

enum class BoardCellState {NORMAL, PASSED, GOLD}

/**
 * Dùng để khởi tạo boardCell
 */
object BoardCellFactory {
    fun newInstance(state: BoardCellState, number: Int) : BoardCell {
        return when(state) {
            BoardCellState.NORMAL -> BoardCell.NormalBoardCell(number)
            BoardCellState.PASSED -> BoardCell.PassedBoardCell(number)
            BoardCellState.GOLD  -> BoardCell.GoldBoardCell(number)
        }
    }
}

/**
 * Trả về trạng thái của boardCell
 */
fun BoardCell.getBoardCellState() : BoardCellState? {
    val map = mapOf(
            BoardCell.NormalBoardCell::class to BoardCellState.NORMAL,
            BoardCell.PassedBoardCell::class to BoardCellState.PASSED,
            BoardCell.GoldBoardCell::class to BoardCellState.GOLD
    )
    return map[this::class]
}