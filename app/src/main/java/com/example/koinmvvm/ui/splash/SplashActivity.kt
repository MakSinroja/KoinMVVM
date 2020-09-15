package com.example.koinmvvm.ui.splash

import androidx.lifecycle.LifecycleOwner
import com.example.koinmvvm.BR
import com.example.koinmvvm.R
import com.example.koinmvvm.base.BaseActivity
import com.example.koinmvvm.databinding.ActivitySplashBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashActivity : BaseActivity<ActivitySplashBinding, SplashViewModel>() {

    private val model: SplashViewModel by viewModel()

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
        model.failureMessage.removeObservers(this@SplashActivity)
        model.successMessage.removeObservers(this@SplashActivity)
        model.warningMessage.removeObservers(this@SplashActivity)
    }

    private fun performViewModelVariableBinding() {
        model.splashActivity = this@SplashActivity
        model.initialization()
    }

    private fun setListeners() {
        model.failureMessage.observe(this@SplashActivity, {
            onFailure(it)
        })

        model.successMessage.observe(this@SplashActivity, {
            onSuccess(it)
        })

        model.warningMessage.observe(this@SplashActivity, {
            onWarning(it)
        })
    }
}