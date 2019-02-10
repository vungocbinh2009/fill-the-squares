package com.binh.games.numberandmaze.database.gamedata

import io.realm.DynamicRealm
import io.realm.RealmMigration

/**
 * Lớp này sẽ được sử dụng trong tình huống có sự thay đổi
 * về cấu trúc database qua các phiên bản của app
 */
class GameDataMigration : RealmMigration {
    override fun migrate(realm: DynamicRealm, oldVersion: Long, newVersion: Long) {
        // Do nothing.
    }
}