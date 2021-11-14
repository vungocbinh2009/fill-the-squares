package com.binh.games.fillthesquares.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.binh.games.fillthesquares.core.basic.*

class ClassicGameViewModel : ViewModel() {
    private lateinit var gameManager: IGameManager
    fun build(gameManager: IGameManager) : ClassicGameViewModel {
        this.gameManager = gameManager
        return this
    }

    fun getScore() : LiveData<Int> {
        return getSomething(gameManager.getScore())
    }

    fun getChangedBoardCell() : LiveData<List<Pair<Int, Int>>> {
        return getSomething(gameManager.getBoard().getChangedBoardCellPosition())
    }

    fun getPlayerCurrentPosition() : LiveData<Pair<Int, Int>> {
        return getSomething(gameManager.getPlayer().getCurrentPosition())
    }

    fun getPlayerPreviousPosition() : LiveData<Pair<Int, Int>> {
        return getSomething(gameManager.getPlayer().getPreviousPosition())
    }

    fun getPlayerCurrentPossibleMove() : LiveData<List<Pair<Int, Int>>> {
        return getSomething(gameManager.getPlayer().getPossibleMove(gameManager.getBoard()))
    }

    fun getPlayerPreviousPossibleMove() : LiveData<List<Pair<Int, Int>>> {
        return getSomething(gameManager.getPlayer().getPreviousPossibleMove(gameManager.getBoard()))
    }

    fun getGameState() : LiveData<GameState> {
        return getSomething(gameManager.getGameState())
    }

    private fun <T> getSomething(value: T) : LiveData<T> {
        val result = MutableLiveData<T>()
        result.value = value
        return result
    }

    fun move(direction: MoveDirection) : ClassicGameViewModel {
        gameManager.move(direction)
        return this
    }

    fun getAllBoardCell() : LiveData<List<Pair<Int, Int>>> {
        return getSomething(gameManager.getBoard().getAllBoardCellPosition(BoardCellState.NORMAL, BoardCellState.PASSED, BoardCellState.GOLD))
    }

    fun getBoardCell(position: Pair<Int, Int>) : BoardCell {
        return gameManager.getBoard().getBoardCell(position).first()
    }
}