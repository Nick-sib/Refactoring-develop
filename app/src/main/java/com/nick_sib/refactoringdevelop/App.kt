package com.nick_sib.refactoringdevelop

import android.app.Application
import com.nick_sib.refactoringdevelop.di.application
import com.nick_sib.refactoringdevelop.di.descriprionScreen
//import com.nick_sib.refactoringdevelop.di.historyScreen
//import com.nick_sib.refactoringdevelop.di.descriprionScreen
import com.nick_sib.refactoringdevelop.di.mainScreen
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class App: Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        startKoin { androidContext(this@App) }
//        startKoin {
//            androidContext(applicationContext)
//            modules(listOf(application, mainScreen, descriprionScreen/*, historyScreen*/))
//        }
    }

    companion object {
        lateinit var instance: App
            private set
    }

}