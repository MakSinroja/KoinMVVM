package com.example.koinmvvm.di.module

import com.example.koinmvvm.api.repositories.topHeadLinesRepository.TopHeadLinesRepository
import org.koin.dsl.module

val apiRepositoryModule = module {
    factory { TopHeadLinesRepository(get()) }
}