package com.binh.games.fillthesquares.core.classicgame

import com.binh.games.fillthesquares.core.basic.BoardCell
import com.binh.games.fillthesquares.core.basic.IBoard
import com.binh.games.fillthesquares.core.basic.IGameManager
import com.binh.games.fillthesquares.core.basic.IPlayer

/**
 * Lớp này mô tả 1 manager trong màn chơi classic.
 */
class ClassicGameManager(override val board: IBoard, override var player: IPlayer, override var gameState: IGameManager.GameState, override var score: Int) : IGameManager {
    override fun up(): IGameManager {
        player.up(board)
        updateGame()
        return this
    }

    override fun down(): IGameManager {
        player.down(board)
        updateGame()
        return this
    }

    override fun left(): IGameManager {
        player.left(board)
        updateGame()
        return this
    }

    override fun right(): IGameManager {
        player.right(board)
        updateGame()
        return this
    }

    private fun updateGame() {
        score -= board.getCell(player.previousPlayerPosition).number
        score--
        checkGameState()
    }

    /**
     * Hàm này chứa logic kiểm tra xem người chơi thắng / thua / đang chơi.
     */
    private fun checkGameState() {
        if (board.getCell(player.playerPosition) is BoardCell.WonBoardCell) {
            gameState = IGameManager.GameState.WON
        }

        if (score <= 0) {
            gameState = IGameManager.GameState.LOST
        }
    }
}