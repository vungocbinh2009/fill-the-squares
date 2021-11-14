package com.binh.games.fillthesquares.core.basic

/**
 * Các trạng thái của game
 */
enum class GameState {PLAYING, DONE}

/**
 * Interface quy định các hàm dùng để quản lý màn chơi.
 */
interface IGameManager {
    fun move(direction: MoveDirection) : IGameManager

    fun getScore() : Int

    fun getGameState() : GameState

    fun getPlayer() : IPlayer

    fun getBoard() : IBoard
}