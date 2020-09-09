package com.example.koinmvvm.ui.splash

import android.app.Application
import com.example.koinmvvm.base.BaseViewModel
import com.example.smileyprogressview.listeners.OnAnimPerformCompletedListener

class SplashViewModel constructor(application: Application) : BaseViewModel(application),
    OnAnimPerformCompletedListener {

    lateinit var splashActivity: SplashActivity

    fun initialization() {
        splashActivity.getViewModelDataBinding().apply {
            smileyProgressView.setOnAnimPerformCompletedListener(this@SplashViewModel)
            smileyProgressView.start()
        }
    }

    override fun onStart() {}

    override fun onCompleted() {}
}