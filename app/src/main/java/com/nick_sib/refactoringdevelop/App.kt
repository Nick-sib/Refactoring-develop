package com.nick_sib.refactoringdevelop

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class App: Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        startKoin { androidContext(this@App) }
    }


    companion object {
        const val PREFS_KEY_THEME = "theme"

        lateinit var instance: App
            private set
    }

}