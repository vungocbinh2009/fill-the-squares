package com.binh.games.numberandmaze

import android.app.Application
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware

class MainApplication : Application(), KodeinAware {

    override val kodein: Kodein = Kodein {
        // Nothing
    }
}