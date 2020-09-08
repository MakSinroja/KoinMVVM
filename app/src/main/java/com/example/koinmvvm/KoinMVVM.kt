package com.example.koinmvvm

import androidx.multidex.MultiDexApplication
import com.example.koinmvvm.di.injector.AppInjector
import com.example.koinmvvm.utils.CrashReportingTree
import timber.log.Timber

class KoinMVVM : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree()) else Timber.plant(CrashReportingTree())

        AppInjector.initialization(this@KoinMVVM)
    }
}