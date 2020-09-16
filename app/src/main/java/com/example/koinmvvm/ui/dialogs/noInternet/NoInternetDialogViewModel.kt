package com.example.koinmvvm.ui.dialogs.noInternet

import android.app.Application
import androidx.fragment.app.FragmentActivity
import com.example.koinmvvm.base.BaseViewModel

class NoInternetDialogViewModel constructor(application: Application) : BaseViewModel(application) {

    lateinit var noInternetDialogFragmentActivity: FragmentActivity

    override fun initialization() {}

    override fun snackBarMessagesObserver() {}

    override fun removeSnackBarMessagesObserver() {}
}