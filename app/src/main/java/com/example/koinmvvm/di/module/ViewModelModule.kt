package com.example.koinmvvm.di.module

import com.example.koinmvvm.ui.dialogs.exitApp.ExitAppViewModel
import com.example.koinmvvm.ui.dialogs.noInternet.NoInternetDialogViewModel
import com.example.koinmvvm.ui.newsPage.NewsPageViewModel
import com.example.koinmvvm.ui.newsPage.bookmarkedNewsPage.BookmarkedNewsPageViewModel
import com.example.koinmvvm.ui.newsPage.newsWebView.NewsWebViewViewModel
import com.example.koinmvvm.ui.splash.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    /* Activity View Models */
    viewModel { SplashViewModel(get()) }
    viewModel { NewsPageViewModel(get(), get(), get()) }
    viewModel { BookmarkedNewsPageViewModel(get(), get()) }
    viewModel { NewsWebViewViewModel(get()) }

    /* Fragment View Models */

    /* Dialog View Models */
    viewModel { NoInternetDialogViewModel(get()) }
    viewModel { ExitAppViewModel(get()) }
}