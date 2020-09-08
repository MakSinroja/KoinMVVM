package com.example.koinmvvm.di.module

import com.example.koinmvvm.KoinMVVM
import com.example.koinmvvm.preferences.CommonPreferences
import org.koin.dsl.module

val appModule = module {
    single { get<KoinMVVM>() }

    single { CommonPreferences(get()) }
}