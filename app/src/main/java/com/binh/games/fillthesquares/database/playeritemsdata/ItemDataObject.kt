package com.binh.games.fillthesquares.database.playeritemsdata

import com.binh.games.fillthesquares.gamespecialitem.Item
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * Đây là class chứa dữ liệu về 1 item nào đó mà người chơi
 * đã mua trong game.
 */
open class ItemDataObject(
        /**
         * Đây là thuộc tính chỉ loại item mà người chơi đã mua.
         * Nó chính là tên của class tương ứng với item đó
         */
        @PrimaryKey
        var itemType: String = Item::class.java.canonicalName!!,
        /**
         * Đây là số lượng của item đó mà người chơi đã mua.
         */
        var quantity: Int = 0 // So luong
) : RealmObject()