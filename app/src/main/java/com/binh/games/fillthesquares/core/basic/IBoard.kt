package com.binh.games.fillthesquares.core.basic

/**
 * Đây là interface biểu diễn 1 bản đồ trong trò chơi.
 * Để có 1 bản đồ bạn cần cung cấp kích thước và các ô board cell trong bản đồ.
 * Sau đó bạn cần cung cấp cách để người chơi di chuyển được trên bản đồ
 * 4 hàm cuối cùng cung cấp các phương thức để module cao hơn "vẽ" bản đồ.
 */
interface IBoard {
    /**
     * Kích thước của bảng.
     */
    val size: Pair<Int, Int>

    /**
     * Trả về ô tiếp theo trong bảng khi người chơi muốn đi lên trên.
     */
    fun nextCellUp(current: Pair<Int, Int>) : Pair<Int, Int>

    /**
     * Trả về ô tiếp theo trong bảng khi người chơi muốn đi xuống dưới.
     */
    fun nextCellDown(current: Pair<Int, Int>) : Pair<Int, Int>

    /**
     * Trả về ô tiếp theo trong bảng khi người chơi muốn đi sang trái.
     */
    fun nextCellLeft(current: Pair<Int, Int>) : Pair<Int, Int>

    /**
     * Trả về ô tiếp theo trong bảng khi người chơi muốn đi sang phải.
     */
    fun nextCellRight(current: Pair<Int, Int>) : Pair<Int, Int>

    /**
     * Trả về danh sách các ô "tàng hình" trong bảng.
     * (Người chơi sẽ không nhìn thấy các ô này khi chơi)
     */
    fun invisibleBoardCell() : List<Pair<Int, Int>>

    /**
     * Trả về các ô mà người chơi khi đi vào đó sẽ thắng.
     * (Các ô này sẽ được tô màu xanh trên bản đồ)
     */
    fun wonBoardCell() : List<Pair<Int, Int>>

    /**
     * Trả về các ô mà người chơi sẽ thua nếu đi vào đó.
     * (Các ô này sẽ được tô màu đỏ (nếu có).
     */
    fun lostBoardCell() : List<Pair<Int, Int>>

    /**
     * Trả về ô trong bảng tại 1 vị trí cho trước.
     */
    fun getCell(position: Pair<Int, Int>) : BoardCell

    /**
     * Thay đổi ô trong bảng tại 1 vị trí cho trước.
     */
    fun setCell(position: Pair<Int, Int>, cell: BoardCell) : IBoard
}