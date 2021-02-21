package com.nick_sib.refactoringdevelop

import android.app.Activity
import android.app.Application
import com.nick_sib.refactoringdevelop.di.DaggerAppComponent
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject


class App: Application(), HasActivityInjector {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector(): DispatchingAndroidInjector<Activity> {
        return dispatchingAndroidInjector
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        DaggerAppComponent.builder()
            .application(this)
            .build()
            .inject(this)
    }


    companion object {
        lateinit var instance: App
            private set
    }

}