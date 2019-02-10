package com.binh.games.numberandmaze.database.gamedata

import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.Sort

class GameStatisticDatabaseImpl : IGameStatisticDatabase {
    private lateinit var database: Realm

    override fun openDatabase(): IGameStatisticDatabase {
        val config = RealmConfiguration.Builder()
                .name("gameData.realm")
                .schemaVersion(1)
                .build()
        database = Realm.getInstance(config)
        return this
    }

    override fun getTotalPlayedGame(gameMode: String): Long {
        return database.where(GameDataObject::class.java).equalTo("gameMode",gameMode).count()
    }

    override fun getTotalPlayedGameWon(gameMode: String): Long {
        return database.where(GameDataObject::class.java).equalTo("gameMode", gameMode) .greaterThan("score", -1).count()
    }

    override fun getTotalPlayedGameInAllMode(): Long {
        return database.where(GameDataObject::class.java).count()
    }

    override fun getTotalPlayedGameWonInAllMode(): Long {
        return database.where(GameDataObject::class.java).greaterThan("score", -1).count()
    }

    override fun getHighestScore(gameMode: String): Int? {
        return database.where(GameDataObject::class.java).equalTo("gameMode", gameMode).max("score")?.toInt()
    }

    override fun getLowestScore(gameMode: String): Int? {
        return database.where(GameDataObject::class.java).equalTo("gameMode", gameMode).min("score")?.toInt()
    }

    override fun getAverageScore(gameMode: String): Double {
        return database.where(GameDataObject::class.java).equalTo("gameMode", gameMode).average("score")
    }

    override fun getTopScore(numberOfGame: Int, gameMode: String): List<GameDataObject> {
        return database.where(GameDataObject::class.java).equalTo("gameMode", gameMode).sort("score", Sort.DESCENDING,"date",Sort.DESCENDING).limit(10).findAll()
    }

    override fun addGameData(data: GameDataObject): IGameStatisticDatabase {
        database.beginTransaction()
        database.copyToRealmOrUpdate(data)
        database.commitTransaction()
        return this
    }

    override fun removeAllGames(vararg gameMode: String): IGameStatisticDatabase {
        database.beginTransaction()
        for (game in gameMode) {
            database.where(GameDataObject::class.java).equalTo("gameMode", game).findAll().deleteAllFromRealm()
        }
        database.commitTransaction()
        return this
    }

    override fun findGameById(id: String): GameDataObject? {
        return database.where(GameDataObject::class.java).equalTo("gameId", id).findFirst()
    }

    override fun closeDatabase() {
        database.close()
    }
}