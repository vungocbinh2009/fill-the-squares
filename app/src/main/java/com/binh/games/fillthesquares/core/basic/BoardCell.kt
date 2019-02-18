package com.binh.games.fillthesquares.core.basic

/**
 * Đây là class cơ bản nhất của trò chơi.
 * Nó biểu diễn 1 ô trong "Bản đồ" của trò chơi.
 * Nó lưu giữ các thông tin sau:
 * [number] : con số nằm trong ô.
 * state: Mỗi class ở dưới tương ứng với 1 trạng thái của ô.
 * visible: nhìn thấy, invisible: không nhìn thấy,
 * lost: Người chơi đi vào ô này thì thua.
 * won: Người chơi đi vào ô này thì thắng.
 */
sealed class BoardCell(var number: Int) {
    class VisibleBoardCell(number: Int) : BoardCell(number)
    class InvisibleBoardCell(number: Int) : BoardCell(number)
    class LostBoardCell(number: Int) : BoardCell(number)
    class WonBoardCell(number: Int) : BoardCell(number)
}