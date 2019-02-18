package com.binh.games.fillthesquares.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.binh.games.fillthesquares.core.basic.IGameManager
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware

/**
 * Đây là ViewModel được dùng để cung cấp các thông tin co bản
 * cho ClassicGameActivity trong việc biểu thị trò chơi trên màn hình.
 */
class ClassicGameViewModel : ViewModel(), KodeinAware {
    /**
     * A Kodein Aware class must be within reach of a [Kodein] object.
     */
    override lateinit var kodein: Kodein
    /**
     * Thuộc tính này biểu diễn đối tượng GameManager mà ViewModel
     * sẽ sử dụng để lấy các thông tin cần thiết, cung cấp cho Activity.
     */
    lateinit var gameManager: IGameManager

    /**
     * Phương thức này dùng để xây dựng 1 đối tượng viewModel.
     * Trước khi sử dụng viewModel ta bắt buộc phải gọi phương thức này,
     * nếu không sẽ xảy ra lỗi.
     */
    fun build(gameManager: IGameManager): ClassicGameViewModel {
        this.gameManager = gameManager
        return this
    }

    /**
     * Phương thức này cung cấp giá trị của 1 ô cho trước trong bảng.
     */
    fun boardNumber(position: Pair<Int, Int>) : LiveData<Int> {
        val result = MutableLiveData<Int>()
        result.value = gameManager.board.getCell(position).number
        return result
    }

    /**
     * Phương thức này cung cấp cho activity vị trí của người chơi hiện tại.
     */
    fun playerPosition(): LiveData<Pair<Int, Int>> {
        val result = MutableLiveData<Pair<Int, Int>>()
        result.value = gameManager.player.playerPosition
        return result
    }

    /**
     * Phương thức này cung cấp vị trí đích mà người chơi cần phải đến
     * để dành chiến thắng.
     */
    fun wonPosition(): LiveData<List<Pair<Int, Int>>> {
        val result = MutableLiveData<List<Pair<Int, Int>>>()
        result.value = gameManager.board.wonBoardCell()
        return result
    }

    /**
     * Phương thức này cung cấp điểm số hiện tại của người chơi.
     */
    fun score(): LiveData<Int> {
        val result = MutableLiveData<Int>()
        result.value = gameManager.score
        return result
    }

    /**
     * Phương thức này cung cấp trạng thái hiện tại của trò chơi,
     * (WON, LOST, PLAYING)
     */
    fun gameState(): LiveData<IGameManager.GameState> {
        val result = MutableLiveData<IGameManager.GameState>()
        result.value = gameManager.gameState
        return result
    }

    /**
     * Phương thức này cung cấp vị trí của người chơi trước đó.
     * (vị trí trước khi di chuyển đến vị trí hiện tại).
     */
    fun previousPlayerPosition() : LiveData<Pair<Int, Int>> {
        val result = MutableLiveData<Pair<Int, Int>>()
        result.value = gameManager.player.previousPlayerPosition
        return result
    }

    /**
     * Hàm này trả về danh sách các ô mà người chơi đã đi qua
     * trong nước đi trước đó.
     */
    fun playerMoveAllCell(): LiveData<List<Pair<Int, Int>>> {
        val result = MutableLiveData<List<Pair<Int, Int>>>()
        result.value = gameManager.player.playerMoveAllCell()
        return result
    }

    fun playerPossibleMove() : LiveData<List<Pair<Int, Int>>> {
        val result = MutableLiveData<List<Pair<Int, Int>>>()
        result.value = gameManager.getPossibleMove()
        return result
    }

    fun previousPlayerPossibleMove() : LiveData<List<Pair<Int, Int>>> {
        val result = MutableLiveData<List<Pair<Int, Int>>>()
        result.value = gameManager.getPreviousPossibleMove()
        return result
    }

    /**
     * Phương thức này giúp người chơi di chuyển lên trên.
     */
    fun up() {
        gameManager.up()
    }

    /**
     * Phương thức này giúp người chơi di chuyển xuống dưới.
     */
    fun down() {
        gameManager.down()
    }

    /**
     * Phương thức này giúp người chơi di chuyển sang trái.
     */
    fun left() {
        gameManager.left()
    }

    /**
     * Phương thức này giúp người chơi di chuyển sang phải.
     */
    fun right() {
        gameManager.right()
    }
}