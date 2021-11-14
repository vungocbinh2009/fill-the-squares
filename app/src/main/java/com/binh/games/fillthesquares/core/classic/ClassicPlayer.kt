package com.binh.games.fillthesquares.core.classic

import com.binh.games.fillthesquares.core.basic.*

class ClassicPlayer(startPosition: Pair<Int, Int>) : IPlayer{
    private var currentPosition = startPosition
    private var previousPosition = startPosition
    private var possibleChangeBoardCell: MutableList<Pair<Int, Int>> = ArrayList()
    private var moveScore : Int = 0

    override fun move(direction: MoveDirection, board: IBoard): Boolean {
        var result = currentPosition
        moveScore = 0
        possibleChangeBoardCell = mutableListOf(result)
        for (i in 0 until board.getBoardCell(result).first().number) {
            val temp = board.nextCell(direction, result)
            if (temp == result) {
                moveScore += 3
            } else {
                result = temp
                moveScore += when (board.getBoardCell(temp).first().getBoardCellState()) {
                    BoardCellState.NORMAL -> 1
                    BoardCellState.PASSED -> 2
                    else -> 1
                }
                possibleChangeBoardCell.add(result)
            }
        }
        previousPosition = currentPosition
        currentPosition = result
        moveScore++
        if (possibleChangeBoardCell.size == 1) {
            return false
        }
        return true
    }

    override fun getCurrentPosition(): Pair<Int, Int> {
        return currentPosition
    }

    override fun getPreviousPosition(): Pair<Int, Int> {
        return previousPosition
    }

    override fun getPossibleMove(board: IBoard): List<Pair<Int, Int>> {
        return generatePossibleMove(currentPosition, board)
    }

    override fun getPreviousPossibleMove(board: IBoard): List<Pair<Int, Int>> {
        return generatePossibleMove(previousPosition, board)
    }

    override fun getPossibleChangeBoardCell(): List<Pair<Int, Int>> {
        return possibleChangeBoardCell
    }

    override fun moveScore(): Int {
        return moveScore
    }

    private fun generatePossibleMove(position: Pair<Int, Int>, board: IBoard) : List<Pair<Int, Int>> {
        val backup1 = currentPosition
        val backup2 = previousPosition
        val backup3 = possibleChangeBoardCell
        val result = mutableListOf<Pair<Int, Int>>()
        val directions = listOf(MoveDirection.UP, MoveDirection.DOWN, MoveDirection.LEFT, MoveDirection.RIGHT)
        for (direction in directions) {
            currentPosition = position
            move(direction, board)
            result.add(currentPosition)
        }
        currentPosition = backup1
        previousPosition = backup2
        possibleChangeBoardCell = backup3
        return result
    }
}