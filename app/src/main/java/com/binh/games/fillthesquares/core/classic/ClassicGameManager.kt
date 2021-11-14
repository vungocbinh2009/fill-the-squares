package com.binh.games.fillthesquares.core.classic

import com.binh.games.fillthesquares.core.basic.*

class ClassicGameManager(private val board: IBoard,private val player: IPlayer) : IGameManager {
    private var score: Int = 0
    private var gameState: GameState = GameState.PLAYING
    override fun move(direction: MoveDirection): IGameManager {
        val canMove = player.move(direction, board)
        val possibleChange = player.getPossibleChangeBoardCell()
        board.updateBoardCell(possibleChange)
        gameState = board.getGameState()
        if (canMove) {
            score += player.moveScore()
        }
        return this
    }

    override fun getScore(): Int {
        return score
    }

    override fun getGameState(): GameState {
        return gameState
    }

    override fun getPlayer(): IPlayer {
        return player
    }

    override fun getBoard(): IBoard {
        return board
    }
}