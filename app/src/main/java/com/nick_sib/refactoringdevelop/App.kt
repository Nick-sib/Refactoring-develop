package com.nick_sib.refactoringdevelop

import android.app.Application
import com.nick_sib.refactoringdevelop.di.application
import com.nick_sib.refactoringdevelop.di.mainScreen
import org.koin.core.context.startKoin


class App: Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        startKoin {
            modules(listOf(application, mainScreen))
        }
    }


    companion object {
        lateinit var instance: App
            private set
    }

}