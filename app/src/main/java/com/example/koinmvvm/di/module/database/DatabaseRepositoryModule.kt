package com.example.koinmvvm.di.module.database

import com.example.koinmvvm.database.repositories.articles.ArticlesRepository
import org.koin.dsl.module

val databaseRepositoryModule = module {
    factory { ArticlesRepository(get()) }
}