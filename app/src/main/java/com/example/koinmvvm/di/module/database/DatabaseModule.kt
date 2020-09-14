package com.example.koinmvvm.di.module.database

import com.example.koinmvvm.database.AppDatabase
import org.koin.dsl.module

val databaseModule = module {
    single { AppDatabase.getInstance(get()) }

    single { get<AppDatabase>().articleDao() }
}