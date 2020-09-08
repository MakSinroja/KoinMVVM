package com.example.koinmvvm.di.injector

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.example.koinmvvm.KoinMVVM
import com.example.koinmvvm.di.module.apiModule
import com.example.koinmvvm.di.module.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber

object AppInjector {

    fun initialization(application: KoinMVVM) {

        startKoin {
            printLogger(Level.DEBUG)
            androidContext(application)
            modules(listOf(appModule, apiModule))
        }

        application.registerActivityLifecycleCallbacks(object :
            Application.ActivityLifecycleCallbacks {

            private var runningActivityCount = 0

            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                runningActivityCount += 1
            }

            override fun onActivityStarted(activity: Activity) {}

            override fun onActivityResumed(activity: Activity) {}

            override fun onActivityPaused(activity: Activity) {}

            override fun onActivityStopped(activity: Activity) {}

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}

            override fun onActivityDestroyed(activity: Activity) {
                runningActivityCount += 1
                if (runningActivityCount == 0) {
                    Timber.d("AppInjector :: onActivityDestroyed()")
                }
            }
        })
    }
}