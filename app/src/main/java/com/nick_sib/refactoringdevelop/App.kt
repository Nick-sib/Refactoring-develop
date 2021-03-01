package com.nick_sib.refactoringdevelop

import android.app.Application
import com.nick_sib.refactoringdevelop.di.application
import com.nick_sib.refactoringdevelop.di.mainScreen
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class App: Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        startKoin {
            androidContext(applicationContext)
            modules(listOf(application, mainScreen))//, historyScreen))
//            modules(listOf(application, mainScreen))
        }
    }

    companion object {
        lateinit var instance: App
            private set
    }

}