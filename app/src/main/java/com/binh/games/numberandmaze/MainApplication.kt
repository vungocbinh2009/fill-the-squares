package com.binh.games.numberandmaze

import android.app.Application
import com.binh.games.numberandmaze.other.*
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

    override val kodein: Kodein = Kodein {
        // Xây dựng các object cần thiết.
        import(basic)
        import(classicGame6x6)
        import(classicGame8x8)
        import(validClassicGame6x6)
        import(validClassicGame8x8)
        import(boardFragment)
    }
}