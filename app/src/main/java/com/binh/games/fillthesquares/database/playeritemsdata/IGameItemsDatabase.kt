package com.binh.games.fillthesquares.database.playeritemsdata

/**
 * Đây là interface giúp các module cấp cao thao tác với dữ liệu về
 * các item mà người chơi đã mua.
 * Tương tự như Game Statistic, cần gọi openDatabase trước khi dùng và
 * closeDatabase sau khi sử dụng xong.
 */
interface IGameItemsDatabase {
    fun openDatabase()

    fun getAvailableItems() : List<ItemDataObject>

    fun addItem(vararg itemType: String)

    fun removeItem(vararg itemType: String)

    fun closeDatabase()
}