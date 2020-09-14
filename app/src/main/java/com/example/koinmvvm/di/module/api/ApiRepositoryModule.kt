package com.example.koinmvvm.di.module.api

import com.example.koinmvvm.api.repositories.topHeadLinesRepository.TopHeadLinesRepository
import org.koin.dsl.module

val apiRepositoryModule = module {
    factory { TopHeadLinesRepository(get()) }
}