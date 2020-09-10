package com.example.koinmvvm.ui.dialogs.noInternet

import android.app.Application
import com.example.koinmvvm.base.BaseViewModel

class NoInternetDialogViewModel constructor(application: Application) : BaseViewModel(application) {

    lateinit var noInternetDialog: NoInternetDialog

    override fun initialization() {

    }
}