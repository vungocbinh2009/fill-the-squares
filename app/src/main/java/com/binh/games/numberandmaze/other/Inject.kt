package com.binh.games.numberandmaze.other

import com.binh.games.numberandmaze.core.basic.BoardCell
import com.binh.games.numberandmaze.core.basic.IBoard
import com.binh.games.numberandmaze.core.basic.IGameManager
import com.binh.games.numberandmaze.core.basic.IPlayer
import com.binh.games.numberandmaze.core.classicgame.ClassicBoard
import com.binh.games.numberandmaze.core.classicgame.ClassicGameManager
import com.binh.games.numberandmaze.core.classicgame.ClassicPlayer
import com.binh.games.numberandmaze.fragment.Board6x6Fragment
import com.binh.games.numberandmaze.fragment.Board8x8Fragment
import com.binh.games.numberandmaze.fragment.IBoardFragment
import com.binh.games.numberandmaze.other.validateClassicBoard
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.factory
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider

/**
 * Nội dung trong file này là các module inject, sử dụng Kodein.
 */


/**
 * Đây là module cơ bản nhất, cung cấp các dependency dùng cho các module sau.
 */
val basic = Kodein.Module ("basic") {
    bind<BoardCell>("random") with factory { randomRange: IntRange -> BoardCell.VisibleBoardCell(randomRange.random()) }

    bind<BoardCell>("visibleWithNumber") with factory { number: Int -> BoardCell.VisibleBoardCell(number)}

    bind<BoardCell>("wonWithNumber") with factory { number: Int -> BoardCell.WonBoardCell(number)}

    bind<IPlayer>("normalPlayer") with provider { ClassicPlayer(Pair(0,0)) }
}

/**
 * Đây là module dùng để cung cấp object [ClassicGameManager] bảng 6x6 dùng cho activity tương ứng
 */
val classicGame6x6 = Kodein.Module ("classicGame6x6") {
    bind<IBoard>("classicBoard6x6") with factory {randomRange: IntRange ->
        val result = ClassicBoard(Pair(6,6), Array(6) { Array(6) { instance<IntRange,BoardCell>("random", arg = randomRange)} })
        result.setCell(Pair(5,5), instance("wonWithNumber", arg = 0))
    }

    bind<IGameManager>("classicGameManager6x6") with provider {
        ClassicGameManager(instance("classicBoard6x6",arg = 1..3), instance("normalPlayer"), IGameManager.GameState.PLAYING, 100)
    }
}

/**
 * Đây là module dùng để cung cấp object [ClassicGameManager] bảng 8x8 dùng cho activity tương ứng.
 */
val classicGame8x8 = Kodein.Module("classicGame8x8") {
    bind<IBoard>("classicBoard8x8") with factory { randomRange: IntRange ->
        val result = ClassicBoard(Pair(8,8), Array(8) { Array(8) { instance<IntRange,BoardCell>("random", arg = randomRange)} })
        result.setCell(Pair(7,7), instance("wonWithNumber", arg = 0))
    }

    bind<IGameManager>("classicGameManager8x8") with provider {
        ClassicGameManager(instance("classicBoard8x8",arg = 1..4), instance("normalPlayer"), IGameManager.GameState.PLAYING, 100)
    }
}

/**
 * Đây là module dùng để cung cấp object [ClassicGameManager] bảng 8x8 dùng cho activity tương ứng.
 *
 * Nhưng nó sẽ đảm bảo bảng cung cấp sẽ có lời giải.
 */
val validClassicGame6x6 = Kodein.Module("validClassicBoard6x6") {
    importOnce(classicGame6x6)
    bind<IBoard>("validClassicBoard6x6") with provider {
        var randomBoard: IBoard = instance("classicBoard6x6", arg = 1..3)
        while (!validateClassicBoard(randomBoard, instance("normalPlayer"), Pair(0,0))) {
            randomBoard = instance("classicBoard6x6", arg = 1..3)
        }
        randomBoard
    }

    bind<IGameManager>("validClassicGameManager6x6") with provider {
        ClassicGameManager(instance("validClassicBoard6x6"), instance("normalPlayer"), IGameManager.GameState.PLAYING, 100)
    }
}

/**
 * Đây là module dùng để cung cấp object [ClassicGameManager] bảng 8x8 dùng cho activity tương ứng.
 *
 * Nhưng nó sẽ đảm bảo bảng cung cấp sẽ có lời giải.
 */
val validClassicGame8x8 = Kodein.Module("validClassicBoard8x8") {
    importOnce(classicGame8x8)
    bind<IBoard>("validClassicBoard8x8") with provider {
        var randomBoard: IBoard = instance("classicBoard8x8", arg = 1..4)
        while (!validateClassicBoard(randomBoard, instance("normalPlayer"), Pair(0,0))) {
            randomBoard = instance("classicBoard8x8", arg = 1..4)
        }
        randomBoard
    }

    bind<IGameManager>("validClassicGameManager8x8") with provider {
        ClassicGameManager(instance("validClassicBoard8x8"), instance("normalPlayer"), IGameManager.GameState.PLAYING, 100)
    }
}

/**
 * Đây là module cung cấp các fragment 6x6 và 8x8 cho các activity cần thiết.
 */
val boardFragment = Kodein.Module("fragment") {
    bind<IBoardFragment>("board6x6") with provider { Board6x6Fragment() }
    bind<IBoardFragment>("board8x8") with provider { Board8x8Fragment() }
}