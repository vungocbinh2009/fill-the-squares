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
 * activity ở dưới và khởi tạo database
 */
class MainApplication : Application(), KodeinAware {

    override val kodein: Kodein = Kodein.lazy {
        // Xây dựng các object cần thiết.
        import(inject)
    }

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
    }
}