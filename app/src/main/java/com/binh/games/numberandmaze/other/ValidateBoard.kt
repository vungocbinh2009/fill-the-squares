package com.binh.games.numberandmaze.other

import com.binh.games.numberandmaze.core.basic.BoardCell
import com.binh.games.numberandmaze.core.basic.IBoard
import com.binh.games.numberandmaze.core.basic.IPlayer

/**
 * Đây là phương thức dùng để kiểm tra xem bảng đã cho liệu có thể giải được hay không
 * (có lời giải hay không)
 */
fun validateClassicBoard(board: IBoard, player: IPlayer, startPosition: Pair<Int, Int>) : Boolean {
    if (board.getCell(startPosition) is BoardCell.WonBoardCell) return true
    val possibleWonBoardCell = LinkedHashSet<Pair<Int, Int>>()
    possibleWonBoardCell.add(startPosition)
    do {
        val size = possibleWonBoardCell.size
        val result = getAllPossibleCellPlayerMove(possibleWonBoardCell, board, player)
        possibleWonBoardCell.addAll(result)
        for (element in possibleWonBoardCell) {
            if (board.getCell(element) is BoardCell.WonBoardCell) {
                return true
            }
        }
    } while (size < possibleWonBoardCell.size)
    return false
}

fun getAllPossibleCellPlayerMove(cellList: Set<Pair<Int, Int>>, board: IBoard, player: IPlayer) : Set<Pair<Int, Int>> {
    val result = LinkedHashSet<Pair<Int, Int>>()
    for (element in cellList) {
        player.playerPosition = element
        result.add(player.up(board))
        result.add(player.down(board))
        result.add(player.left(board))
        result.add(player.right(board))
    }
    return result
}