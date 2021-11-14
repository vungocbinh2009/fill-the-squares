package com.binh.games.fillthesquares.other

import com.binh.games.fillthesquares.core.basic.BoardCell
import com.binh.games.fillthesquares.core.basic.IBoard
import com.binh.games.fillthesquares.core.basic.IGameManager
import com.binh.games.fillthesquares.core.basic.IPlayer
import com.binh.games.fillthesquares.core.classic.ClassicBoard
import com.binh.games.fillthesquares.core.classic.ClassicGameManager
import com.binh.games.fillthesquares.core.classic.ClassicPlayer
import com.binh.games.fillthesquares.database.gamedata.GameStatisticDatabaseImpl
import com.binh.games.fillthesquares.database.gamedata.IGameStatisticDatabase
import com.binh.games.fillthesquares.fragment.Board6x6Fragment
import com.binh.games.fillthesquares.fragment.Board8x8Fragment
import com.binh.games.fillthesquares.fragment.IBoardFragment
import com.binh.games.fillthesquares.other.InjectConstants.BOARD_FRAGMENT_6x6
import com.binh.games.fillthesquares.other.InjectConstants.BOARD_FRAGMENT_8x8
import com.binh.games.fillthesquares.other.InjectConstants.CLASSIC_GAME_MANAGER_6x6
import com.binh.games.fillthesquares.other.InjectConstants.CLASSIC_GAME_MANAGER_6x6_HARD
import com.binh.games.fillthesquares.other.InjectConstants.CLASSIC_GAME_MANAGER_8x8
import com.binh.games.fillthesquares.other.InjectConstants.CLASSIC_GAME_MANAGER_8x8_HARD
import com.binh.games.fillthesquares.other.InjectConstants.GAME_STATISTIC_DATABASE
import com.binh.games.fillthesquares.other.InjectConstants.NORMAL_PLAYER
import com.binh.games.fillthesquares.other.InjectConstants.SQUARE_CLASSIC_BOARD
import com.binh.games.fillthesquares.other.InjectConstants.SQUARE_CLASSIC_BOARD_HARD
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.factory
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider

// Đây là file chứa toàn bộ các inject cần thiết trong ứng dụng.
object InjectConstants {
    const val NORMAL_PLAYER = "normalPlayer"
    const val SQUARE_CLASSIC_BOARD = "squareClassicBoard"
    const val SQUARE_CLASSIC_BOARD_HARD = "squareClassicBoardHard"
    const val CLASSIC_GAME_MANAGER_6x6 = "classicGameManager6x6"
    const val CLASSIC_GAME_MANAGER_8x8 = "classicGameManager8x8"
    const val CLASSIC_GAME_MANAGER_6x6_HARD = "classicGameManager6x6Hard"
    const val CLASSIC_GAME_MANAGER_8x8_HARD = "classicGameManager8x8Hard"
    const val BOARD_FRAGMENT_6x6 = "boardFragment6x6"
    const val BOARD_FRAGMENT_8x8 = "boardFragment8x8"
    const val GAME_STATISTIC_DATABASE = "gameDatabase"
}

val inject = Kodein.Module("Basic") {
    bind<IPlayer>(NORMAL_PLAYER) with provider { ClassicPlayer(Pair(0,0)) }
    bind<IBoard>(SQUARE_CLASSIC_BOARD) with factory { size: Int ->
        ClassicBoard(Pair(size, size), Array(size) {
            Array(size) {
                BoardCell.NormalBoardCell((1..(size/2)).random())
            }
        })
    }
    bind<IBoard>(SQUARE_CLASSIC_BOARD_HARD) with factory { size: Int ->
        ClassicBoard(Pair(size, size), Array(size) {
            Array(size) {
                BoardCell.NormalBoardCell((2..(size - 2)).random())
            }
        })
    }
    bind<IGameManager>(CLASSIC_GAME_MANAGER_6x6) with provider {
        ClassicGameManager(instance(SQUARE_CLASSIC_BOARD,6), instance(NORMAL_PLAYER))
    }
    bind<IGameManager>(CLASSIC_GAME_MANAGER_8x8) with provider {
        ClassicGameManager(instance(SQUARE_CLASSIC_BOARD,8), instance(NORMAL_PLAYER))
    }
    bind<IGameManager>(CLASSIC_GAME_MANAGER_6x6_HARD) with provider {
        ClassicGameManager(instance(SQUARE_CLASSIC_BOARD_HARD,6), instance(NORMAL_PLAYER))
    }
    bind<IGameManager>(CLASSIC_GAME_MANAGER_8x8_HARD) with provider {
        ClassicGameManager(instance(SQUARE_CLASSIC_BOARD_HARD,8), instance(NORMAL_PLAYER))
    }
    bind<IBoardFragment>(BOARD_FRAGMENT_6x6) with provider { Board6x6Fragment() }
    bind<IBoardFragment>(BOARD_FRAGMENT_8x8) with provider { Board8x8Fragment() }
    bind<IGameStatisticDatabase>(GAME_STATISTIC_DATABASE) with provider { GameStatisticDatabaseImpl() }
}