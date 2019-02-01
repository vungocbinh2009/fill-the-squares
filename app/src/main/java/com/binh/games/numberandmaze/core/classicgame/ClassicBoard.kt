package com.binh.games.numberandmaze.core.classicgame

import com.binh.games.numberandmaze.core.basic.BoardCell
import com.binh.games.numberandmaze.core.basic.IBoard

/**
 * Lớp này mô tả 1 bảng trong màn chơi "classic"
 */
class ClassicBoard(override val size: Pair<Int, Int>,private val cellArray: Array<Array<BoardCell>>) : IBoard {

    override fun nextCellUp(current: Pair<Int, Int>): Pair<Int, Int> {
        if (current.first == 0) return current
        return Pair(current.first - 1, current.second)
    }

    override fun nextCellDown(current: Pair<Int, Int>): Pair<Int, Int> {
        if (current.first == size.first - 1) return Pair(0, current.second)
        return Pair(current.first + 1, current.second)
    }

    override fun nextCellLeft(current: Pair<Int, Int>): Pair<Int, Int> {
        if (current.second == 0) return current
        return Pair(current.first, current.second - 1)
    }

    override fun nextCellRight(current: Pair<Int, Int>): Pair<Int, Int> {
        if (current.second == size.second - 1) return Pair(current.first, 0)
        return Pair(current.first, current.second + 1)
    }

    override fun invisibleBoardCell(): List<Pair<Int, Int>> {
        return ArrayList()
    }

    override fun wonBoardCell(): List<Pair<Int, Int>> {
        val result: MutableList<Pair<Int, Int>> = ArrayList()
        for (arrayElement in cellArray) {
            for (element in arrayElement) {
                if (element is BoardCell.WonBoardCell) {
                    result.add(Pair(cellArray.indexOf(arrayElement), arrayElement.indexOf(element)))
                }
            }
        }
        return result
    }

    override fun lostBoardCell(): List<Pair<Int, Int>> {
        return ArrayList()
    }

    override fun getCell(position: Pair<Int, Int>): BoardCell {
        return cellArray[position.first][position.second]
    }

    override fun setCell(position: Pair<Int, Int>, cell: BoardCell) : IBoard {
        cellArray[position.first][position.second] = cell
        return this
    }
}