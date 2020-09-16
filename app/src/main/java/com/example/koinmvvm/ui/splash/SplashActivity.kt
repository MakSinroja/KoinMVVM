package com.example.koinmvvm.ui.splash

import androidx.lifecycle.LifecycleOwner
import com.example.koinmvvm.BR
import com.example.koinmvvm.R
import com.example.koinmvvm.base.BaseActivity
import com.example.koinmvvm.databinding.ActivitySplashBinding
import org.koin.android.ext.android.inject

class SplashActivity : BaseActivity<ActivitySplashBinding, SplashViewModel>() {

    private val model: SplashViewModel by inject()

    override fun fullscreenActivity(): Boolean = true

    override fun transparentActivity(): Boolean = false

    override fun changeStatusBarColor(): Int = R.attr.themeStatusBarColor

    override fun setContentView(): Int = R.layout.activity_splash

    override fun setBindingVariable(): Int = BR.viewModel

    override fun setViewModel(): SplashViewModel = model

    override fun setLifeCycleOwner(): LifecycleOwner = this@SplashActivity

    override fun initialization() {
        performViewModelVariableBinding()
        setListeners()
    }

    override fun onDestroy() {
        super.onDestroy()
        model.removeSnackBarMessagesObserver()
    }

    private fun performViewModelVariableBinding() {
        model.splashActivity = this@SplashActivity
        model.initialization()
    }

    private fun setListeners() {
        model.snackBarMessagesObserver()
    }

    override fun onBackPressed() {
        showExitAppDialog()
    }
}