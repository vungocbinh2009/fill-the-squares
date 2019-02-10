package com.binh.games.numberandmaze.database.gamedata

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

/**
 * Đây là class cơ bản dùng để tạo ra các bản ghi
 * về kết quả của 1 ván chơi trong trò chơi.
 */
open class GameDataObject(
        /**
         * Đây là id của bản ghi, dùng để nhận diện khi cần thiết.
         */
        @PrimaryKey
        var gameId: String = UUID.randomUUID().toString(),
        /**
         * Đây là thuộc tính lưu lại tên người chơi.
         */
        var playerName: String = "player",
        /**
         * Đây là thuộc tính lưu lại điểm số của người chơi.
         * (Có thể quy định thêm 1 số giá trị đặc biệt như -1 nghĩa là người chơi thua..)
         */
        var score: Int = -1,
        /**
         * Đây là thuộc tính lưu lại loại game mà người chơi chơi.
         * Nó sẽ được quy định thống nhất trong GameSelectionActivity
         */
        var gameMode: String = "Unknown",
        /**
         * Đây là thuộc tính lưu lại ngày chơi mà người chơi đã chơi ván
         * game này (Lưu lại đầy đủ ngày giờ, ngày tháng năm..)
         */
        var date: Date = Calendar.getInstance().time
) : RealmObject()