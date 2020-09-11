package com.example.koinmvvm.di.module

import com.example.koinmvvm.ui.dialogs.noInternet.NoInternetDialogViewModel
import com.example.koinmvvm.ui.newsPage.NewsPageViewModel
import com.example.koinmvvm.ui.splash.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { SplashViewModel(get()) }
    viewModel { NoInternetDialogViewModel(get()) }
    viewModel { NewsPageViewModel(get(), get()) }
}