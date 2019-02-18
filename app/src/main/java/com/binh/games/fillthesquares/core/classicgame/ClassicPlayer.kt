package com.binh.games.fillthesquares.core.classicgame

import com.binh.games.fillthesquares.core.basic.IBoard
import com.binh.games.fillthesquares.core.basic.IPlayer

/**
 * Lớp này mô tả 1 người chơi trong màn chơi classic.
 */
class ClassicPlayer(startPosition: Pair<Int, Int>) : IPlayer {
    override var playerPosition: Pair<Int, Int> = startPosition

    /**
     * Biến này lưu lại lích sử các vị trí mà người chơi đã đến trong nàm chơi.
     */
    private val playerPositionHistory: MutableList<Pair<Int, Int>> = ArrayList()

    /**
     * Biến này lưu lại các vị trí mà người chơi đã đi qua trong nước đi trước đó.
     */
    private var playerMoveAllCell: MutableList<Pair<Int, Int>> = ArrayList()

    override var previousPlayerPosition: Pair<Int, Int> = startPosition

    init {
        playerPositionHistory.add(startPosition)
        playerMoveAllCell.add(startPosition)
    }

    override fun playerPositionHistory(): List<Pair<Int, Int>> {
        return playerPositionHistory
    }

    override fun playerMoveAllCell(): List<Pair<Int, Int>> {
        return playerMoveAllCell
    }

    override fun up(board: IBoard): Pair<Int, Int> {
        return playerMove(board, board::nextCellUp)
    }

    override fun down(board: IBoard): Pair<Int, Int> {
        return playerMove(board, board::nextCellDown)
    }

    override fun left(board: IBoard): Pair<Int, Int> {
        return playerMove(board, board::nextCellLeft)
    }

    override fun right(board: IBoard): Pair<Int, Int> {
        return playerMove(board, board::nextCellRight)
    }

    /**
     * Hàm này thực hiện các thao tác cần thiết để người chơi có thể bắt đầu di chuyển.
     */
    private fun playerMove(board: IBoard, move: (Pair<Int, Int>) -> Pair<Int, Int>) : Pair<Int, Int> {
        playerMoveAllCell = ArrayList()
        playerMoveAllCell.add(playerPosition)
        var result = playerPosition
        previousPlayerPosition = playerPosition
        val step = board.getCell(playerPosition).number
        for (i in 1..step) {
            result = move(result)
            playerMoveAllCell.add(result)
        }
        playerPositionHistory.add(result)
        playerPosition = result
        return result
    }
}