package com.example.koinmvvm.di.module

import com.example.koinmvvm.KoinMVVM
import com.example.koinmvvm.preferences.CommonPreferences
import com.example.koinmvvm.ui.dialogs.exitApp.ExitAppDialog
import com.example.koinmvvm.ui.dialogs.noInternet.NoInternetDialog
import com.example.koinmvvm.utils.networkConnection.InternetConnectionObserver
import org.koin.dsl.module

val appModule = module {
    single { get<KoinMVVM>() }

    single { InternetConnectionObserver(get()) }

    single { CommonPreferences(get()) }

    single { NoInternetDialog.getInstance() }

    single { ExitAppDialog.getInstance() }
}