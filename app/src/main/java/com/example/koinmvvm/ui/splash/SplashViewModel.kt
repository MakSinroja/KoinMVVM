package com.example.koinmvvm.ui.splash

import android.app.Application
import android.os.Handler
import com.example.koinmvvm.base.BaseViewModel
import com.example.koinmvvm.ui.newsPage.NewsPageActivity
import com.example.smileyprogressview.listeners.OnAnimPerformCompletedListener
import org.jetbrains.anko.startActivity

class SplashViewModel constructor(application: Application) : BaseViewModel(application),
    OnAnimPerformCompletedListener {

    lateinit var splashActivity: SplashActivity

    override fun initialization() {
        splashActivity.getViewModelDataBinding().apply {
            smileyProgressView.setOnAnimPerformCompletedListener(this@SplashViewModel)
            smileyProgressView.start()
        }

        Handler().postDelayed({
            splashActivity.startActivity<NewsPageActivity>()
            splashActivity.finish()
        }, 2000)
    }

    override fun onStart() {}

    override fun onCompleted() {}
}