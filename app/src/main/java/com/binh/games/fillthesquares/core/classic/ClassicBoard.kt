package com.binh.games.fillthesquares.core.classic

import com.binh.games.fillthesquares.core.basic.*

class ClassicBoard(override val size: Pair<Int, Int>, private val cellMap: Array<Array<BoardCell>>) : IBoard {
    private var changedBoardCell: List<Pair<Int, Int>> = ArrayList()

    override fun nextCell(direction: MoveDirection, currentPosition: Pair<Int, Int>): Pair<Int, Int> {
        when(direction) {
            MoveDirection.UP -> {
                if (currentPosition.first <= 0) return Pair(0, currentPosition.second)
                return Pair(currentPosition.first - 1, currentPosition.second)
            }
            MoveDirection.DOWN -> {
                if (currentPosition.first >= size.first - 1) return Pair(size.first - 1, currentPosition.second)
                return Pair(currentPosition.first + 1, currentPosition.second)
            }
            MoveDirection.LEFT -> {
                if (currentPosition.second <= 0) return Pair(currentPosition.first, 0)
                return Pair(currentPosition.first, currentPosition.second - 1)
            }
            MoveDirection.RIGHT -> {
                if (currentPosition.second >= size.second - 1) return Pair(currentPosition.first, size.second - 1)
                return Pair(currentPosition.first, currentPosition.second + 1)
            }
        }
    }

    override fun setBoardCellState(state: BoardCellState, vararg positions: Pair<Int, Int>) {
        for (position in positions) {
            val number = getBoardCell(position).first().number
            val cell = BoardCellFactory.newInstance(state, number)
            setBoardCell(position, cell)
        }
    }

    override fun setBoardCellNumber(number: Int, vararg positions: Pair<Int, Int>) {
        getBoardCell(*positions).forEach {
            it.number = number
        }
    }

    override fun getBoardCell(vararg positions: Pair<Int, Int>): List<BoardCell> {
        val result = ArrayList<BoardCell>()
        positions.forEach {
            result.add(cellMap[it.first][it.second])
        }
        return result
    }

    override fun getAllBoardCellPosition(vararg state: BoardCellState): List<Pair<Int, Int>> {
        val result = ArrayList<Pair<Int, Int>>()
        for (i in cellMap.indices) {
            for (j in cellMap[i].indices) {
                if (cellMap[i][j].getBoardCellState() in state) {
                    result.add(Pair(i,j))
                }
            }
        }
        return result
    }

    override fun updateBoardCell(possibleChangeBoardCell: List<Pair<Int, Int>>) {
        setBoardCellState(BoardCellState.PASSED, *possibleChangeBoardCell.toTypedArray())
        changedBoardCell = possibleChangeBoardCell
    }

    override fun getChangedBoardCellPosition(): List<Pair<Int, Int>> {
        return changedBoardCell
    }

    private fun setBoardCell(position: Pair<Int, Int>, cell: BoardCell) {
        cellMap[position.first][position.second] = cell
    }

    override fun getGameState(): GameState {
        cellMap.forEach {
            it.forEach { boardCell ->
                if (boardCell.getBoardCellState() != BoardCellState.PASSED) return GameState.PLAYING
            }
        }
        return GameState.DONE
    }
}