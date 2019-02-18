package com.binh.games.fillthesquares

import android.app.Application
import com.binh.games.fillthesquares.other.*
import io.realm.Realm
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware

/**
 * Đây là lớp cao nhất trong 1 ứng dụng android.
 * Nó sẽ được dùng ngay khi ứng dụng bắt đầu chạy.
 *
 * Hiện tại nó được dùng để cung cấp các dependency cho các
 * activity ở dưới.
 */
class MainApplication : Application(), KodeinAware {

    override val kodein: Kodein = Kodein.lazy {
        // Xây dựng các object cần thiết.
        import(basic)
        import(classicGame6x6)
        import(classicGame8x8)
        import(validClassicGame6x6)
        import(validClassicGame8x8)
        import(boardFragment)
        import(gameDatabase)
    }

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
    }
}