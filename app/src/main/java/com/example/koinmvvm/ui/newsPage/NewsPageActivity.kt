package com.example.koinmvvm.ui.newsPage

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.example.koinmvvm.BR
import com.example.koinmvvm.R
import com.example.koinmvvm.base.BaseActivity
import com.example.koinmvvm.databinding.ActivityNewsPageBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class NewsPageActivity : BaseActivity<ActivityNewsPageBinding, NewsPageViewModel>() {

    private val model: NewsPageViewModel by viewModel()

    override fun fullscreenActivity(): Boolean = false

    override fun transparentActivity(): Boolean = false

    override fun changeStatusBarColor(): Int = R.attr.themeStatusBarColor

    override fun setContentView(): Int = R.layout.activity_news_page

    override fun setBindingVariable(): Int = BR.viewModel

    override fun setViewModel(): NewsPageViewModel = model

    override fun setLifeCycleOwner(): LifecycleOwner = this@NewsPageActivity

    override fun initialization() {
        performViewModelVariableBinding()
        setListeners()
    }

    override fun onDestroy() {
        super.onDestroy()
        model.failureMessage.removeObservers(this@NewsPageActivity)
        model.successMessage.removeObservers(this@NewsPageActivity)
        model.warningMessage.removeObservers(this@NewsPageActivity)
    }

    private fun performViewModelVariableBinding() {
        model.newsPageActivity = this@NewsPageActivity
        model.initialization()
    }

    private fun setListeners() {
        model.failureMessage.observe(this@NewsPageActivity, Observer {
            onFailure(it)
        })

        model.successMessage.observe(this@NewsPageActivity, Observer {
            onSuccess(it)
        })

        model.warningMessage.observe(this@NewsPageActivity, Observer {
            onWarning(it)
        })
    }
}