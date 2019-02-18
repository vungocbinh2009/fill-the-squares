package com.binh.games.numberandmaze.other

import com.binh.games.numberandmaze.core.basic.BoardCell
import com.binh.games.numberandmaze.core.basic.IBoard
import com.binh.games.numberandmaze.core.basic.IGameManager
import com.binh.games.numberandmaze.core.basic.IPlayer
import com.binh.games.numberandmaze.core.classicgame.ClassicBoard
import com.binh.games.numberandmaze.core.classicgame.ClassicGameManager
import com.binh.games.numberandmaze.core.classicgame.ClassicPlayer
import com.binh.games.numberandmaze.database.gamedata.GameStatisticDatabaseImpl
import com.binh.games.numberandmaze.database.gamedata.IGameStatisticDatabase
import com.binh.games.numberandmaze.fragment.Board6x6Fragment
import com.binh.games.numberandmaze.fragment.Board8x8Fragment
import com.binh.games.numberandmaze.fragment.IBoardFragment
import com.binh.games.numberandmaze.other.InjectConstant.BASIC_MODULE
import com.binh.games.numberandmaze.other.InjectConstant.BOARD_6X6_FRAGMENT
import com.binh.games.numberandmaze.other.InjectConstant.BOARD_8X8_FRAGMENT
import com.binh.games.numberandmaze.other.InjectConstant.CLASSIC_BOARD_6X6
import com.binh.games.numberandmaze.other.InjectConstant.CLASSIC_BOARD_6X6_WITH_VALIDATION
import com.binh.games.numberandmaze.other.InjectConstant.CLASSIC_BOARD_8X8
import com.binh.games.numberandmaze.other.InjectConstant.CLASSIC_BOARD_8X8_WITH_VALIDATION
import com.binh.games.numberandmaze.other.InjectConstant.CLASSIC_GAME_6X6_MODULE
import com.binh.games.numberandmaze.other.InjectConstant.CLASSIC_GAME_6X6_MODULE_WITH_VALIDATION
import com.binh.games.numberandmaze.other.InjectConstant.CLASSIC_GAME_8X8_MODULE
import com.binh.games.numberandmaze.other.InjectConstant.CLASSIC_GAME_8X8_MODULE_WITH_VALIDATION
import com.binh.games.numberandmaze.other.InjectConstant.CLASSIC_GAME_MANAGER_6X6
import com.binh.games.numberandmaze.other.InjectConstant.CLASSIC_GAME_MANAGER_6X6_WITH_VALIDATION
import com.binh.games.numberandmaze.other.InjectConstant.CLASSIC_GAME_MANAGER_8X8
import com.binh.games.numberandmaze.other.InjectConstant.CLASSIC_GAME_MANAGER_8X8_WITH_VALIDATION
import com.binh.games.numberandmaze.other.InjectConstant.DATABASE_MODULE
import com.binh.games.numberandmaze.other.InjectConstant.FRAGMENT_MODULE
import com.binh.games.numberandmaze.other.InjectConstant.GAME_STATISTIC
import com.binh.games.numberandmaze.other.InjectConstant.NORMAL_PLAYER
import com.binh.games.numberandmaze.other.InjectConstant.RANDOM_BOARD_CELL
import com.binh.games.numberandmaze.other.InjectConstant.VISIBLE_WITH_NUMBER_BOARD_CELL
import com.binh.games.numberandmaze.other.InjectConstant.WON_WITH_NUMBER_BOARD_CELL
import org.kodein.di.Kodein
import org.kodein.di.generic.*

object InjectConstant {
    const val BASIC_MODULE = "basic"
    const val RANDOM_BOARD_CELL = "random"
    const val VISIBLE_WITH_NUMBER_BOARD_CELL = "visibleWithNumber"
    const val WON_WITH_NUMBER_BOARD_CELL = "wonWithNumber"
    const val NORMAL_PLAYER = "normalPlayer"
    const val CLASSIC_GAME_6X6_MODULE = "classicGame6x6"
    const val CLASSIC_BOARD_6X6 = "classicBoard6x6"
    const val CLASSIC_GAME_MANAGER_6X6 = "classicGameManager6x6"
    const val CLASSIC_GAME_8X8_MODULE = "classicGame8x8"
    const val CLASSIC_BOARD_8X8 = "classicBoard8x8"
    const val CLASSIC_GAME_MANAGER_8X8 = "classicGameManager8x8"
    const val CLASSIC_GAME_6X6_MODULE_WITH_VALIDATION = "validClassicGame6x6"
    const val CLASSIC_BOARD_6X6_WITH_VALIDATION = "validClassicBoard6x6"
    const val CLASSIC_GAME_MANAGER_6X6_WITH_VALIDATION = "validClassicGameManager6x6"
    const val CLASSIC_GAME_8X8_MODULE_WITH_VALIDATION = "validClassicGame8x8"
    const val CLASSIC_BOARD_8X8_WITH_VALIDATION = "validClassicBoard8x8"
    const val CLASSIC_GAME_MANAGER_8X8_WITH_VALIDATION = "validClassicGameManager8x8"
    const val FRAGMENT_MODULE = "fragment"
    const val BOARD_6X6_FRAGMENT = "board6x6"
    const val BOARD_8X8_FRAGMENT = "board8x8"
    const val DATABASE_MODULE = "database"
    const val GAME_STATISTIC = "gameStatistic"
}

/**
 * Nội dung trong file này là các module inject, sử dụng Kodein.
 */


/**
 * Đây là module cơ bản nhất, cung cấp các dependency dùng cho các module sau.
 */
val basic = Kodein.Module (BASIC_MODULE) {
    bind<BoardCell>(RANDOM_BOARD_CELL) with factory {
        randomRange: IntRange -> BoardCell.VisibleBoardCell(randomRange.random())
    }

    bind<BoardCell>(VISIBLE_WITH_NUMBER_BOARD_CELL) with factory {
        number: Int -> BoardCell.VisibleBoardCell(number)
    }

    bind<BoardCell>(WON_WITH_NUMBER_BOARD_CELL) with factory {
        number: Int -> BoardCell.WonBoardCell(number)
    }

    bind<IPlayer>(NORMAL_PLAYER) with provider {
        ClassicPlayer(Pair(0,0))
    }
}

/**
 * Đây là module dùng để cung cấp object [ClassicGameManager] bảng 6x6 dùng cho activity tương ứng
 */
val classicGame6x6 = Kodein.Module (CLASSIC_GAME_6X6_MODULE) {
    bind<IBoard>(CLASSIC_BOARD_6X6) with factory { randomRange: IntRange ->
        val result = ClassicBoard(Pair(6,6), Array(6) { Array(6) {
            instance<IntRange,BoardCell>(RANDOM_BOARD_CELL, arg = randomRange)}
        })
        result.setCell(Pair(5,5), instance(WON_WITH_NUMBER_BOARD_CELL, arg = 0))
    }

    bind<IGameManager>(CLASSIC_GAME_MANAGER_6X6) with provider {
        ClassicGameManager(instance(CLASSIC_BOARD_6X6,arg = 1..3),
                instance(NORMAL_PLAYER), IGameManager.GameState.PLAYING, 100)
    }
}

/**
 * Đây là module dùng để cung cấp object [ClassicGameManager] bảng 8x8 dùng cho activity tương ứng.
 */
val classicGame8x8 = Kodein.Module(CLASSIC_GAME_8X8_MODULE) {
    bind<IBoard>(CLASSIC_BOARD_8X8) with factory { randomRange: IntRange ->
        val result = ClassicBoard(Pair(8,8), Array(8) { Array(8) {
            instance<IntRange,BoardCell>(RANDOM_BOARD_CELL, arg = randomRange)}
        })
        result.setCell(Pair(7,7), instance(WON_WITH_NUMBER_BOARD_CELL, arg = 0))
    }

    bind<IGameManager>(CLASSIC_GAME_MANAGER_8X8) with provider {
        ClassicGameManager(instance(CLASSIC_BOARD_8X8,arg = 1..4), instance(NORMAL_PLAYER),
                IGameManager.GameState.PLAYING, 100)
    }
}

/**
 * Đây là module dùng để cung cấp object [ClassicGameManager] bảng 8x8 dùng cho activity tương ứng.
 *
 * Nhưng nó sẽ đảm bảo bảng cung cấp sẽ có lời giải.
 */
val validClassicGame6x6 = Kodein.Module(CLASSIC_GAME_6X6_MODULE_WITH_VALIDATION) {
    importOnce(classicGame6x6)
    bind<IBoard>(CLASSIC_BOARD_6X6_WITH_VALIDATION) with provider {
        var randomBoard: IBoard = instance(CLASSIC_BOARD_6X6, arg = 2..4)
        while (!validateClassicBoard(randomBoard, instance(NORMAL_PLAYER), Pair(0,0))) {
            randomBoard = instance(CLASSIC_BOARD_6X6, arg = 2..4)
        }
        randomBoard
    }

    bind<IGameManager>(CLASSIC_GAME_MANAGER_6X6_WITH_VALIDATION) with provider {
        ClassicGameManager(instance(CLASSIC_BOARD_6X6_WITH_VALIDATION), instance(NORMAL_PLAYER),
                IGameManager.GameState.PLAYING, 100)
    }
}

/**
 * Đây là module dùng để cung cấp object [ClassicGameManager] bảng 8x8 dùng cho activity tương ứng.
 *
 * Nhưng nó sẽ đảm bảo bảng cung cấp sẽ có lời giải.
 */
val validClassicGame8x8 = Kodein.Module(CLASSIC_GAME_8X8_MODULE_WITH_VALIDATION) {
    importOnce(classicGame8x8)
    bind<IBoard>(CLASSIC_BOARD_8X8_WITH_VALIDATION) with provider {
        var randomBoard: IBoard = instance(CLASSIC_BOARD_8X8, arg = 3..6)
        while (!validateClassicBoard(randomBoard, instance(NORMAL_PLAYER), Pair(0,0))) {
            randomBoard = instance(CLASSIC_BOARD_8X8, arg = 3..6)
        }
        randomBoard
    }

    bind<IGameManager>(CLASSIC_GAME_MANAGER_8X8_WITH_VALIDATION) with provider {
        ClassicGameManager(instance(CLASSIC_BOARD_8X8_WITH_VALIDATION), instance(NORMAL_PLAYER),
                IGameManager.GameState.PLAYING, 100)
    }
}

/**
 * Đây là module cung cấp các fragment 6x6 và 8x8 cho các activity cần thiết.
 */
val boardFragment = Kodein.Module(FRAGMENT_MODULE) {
    bind<IBoardFragment>(BOARD_6X6_FRAGMENT) with provider { Board6x6Fragment() }
    bind<IBoardFragment>(BOARD_8X8_FRAGMENT) with provider { Board8x8Fragment() }
}

/**
 *
 */
val gameDatabase = Kodein.Module(DATABASE_MODULE) {
    bind<IGameStatisticDatabase>(GAME_STATISTIC) with provider { GameStatisticDatabaseImpl() }
}
