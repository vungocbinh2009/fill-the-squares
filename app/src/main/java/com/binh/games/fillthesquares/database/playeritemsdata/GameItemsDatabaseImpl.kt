package com.binh.games.fillthesquares.database.playeritemsdata

import io.realm.Realm
import io.realm.RealmConfiguration

class GameItemsDatabaseImpl : IGameItemsDatabase {
    private lateinit var database: Realm
    override fun openDatabase() {
        val config = RealmConfiguration.Builder()
                .name("itemData.realm")
                .schemaVersion(1)
                .build()
        database = Realm.getInstance(config)
    }

    override fun getAvailableItems(): List<ItemDataObject> {
        return database.where(ItemDataObject::class.java).findAll()
    }

    override fun addItem(vararg itemType: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun removeItem(vararg itemType: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun closeDatabase() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}