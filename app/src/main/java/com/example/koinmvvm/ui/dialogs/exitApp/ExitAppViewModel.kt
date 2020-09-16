package com.example.koinmvvm.ui.dialogs.exitApp

import android.app.Application
import com.example.koinmvvm.base.BaseViewModel

class ExitAppViewModel constructor(application: Application) : BaseViewModel(application) {

    lateinit var exitAppDialog: ExitAppDialog

    override fun initialization() {
    }

    override fun snackBarMessagesObserver() {}

    override fun removeSnackBarMessagesObserver() {}

    fun onClickNo() {
        if (exitAppDialog.isVisible)
            exitAppDialog.dismiss()
    }

    fun onClickYes() {
        if (exitAppDialog.isVisible) {
            exitAppDialog.dismiss()
            exitAppDialog.activity?.let { activity ->
                activity.finishAffinity()
            }
        }
    }
}