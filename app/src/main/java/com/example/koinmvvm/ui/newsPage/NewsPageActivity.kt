package com.example.koinmvvm.ui.newsPage

import androidx.lifecycle.LifecycleOwner
import com.example.koinmvvm.BR
import com.example.koinmvvm.R
import com.example.koinmvvm.base.BaseActivity
import com.example.koinmvvm.databinding.ActivityNewsPageBinding
import org.koin.android.ext.android.inject

class NewsPageActivity : BaseActivity<ActivityNewsPageBinding, NewsPageViewModel>() {

    private val model: NewsPageViewModel by inject()

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
        model.removeSnackBarMessagesObserver()
    }

    private fun performViewModelVariableBinding() {
        model.newsPageActivity = this@NewsPageActivity
        model.initialization()
    }

    private fun setListeners() {
        model.snackBarMessagesObserver()
    }

    override fun onBackPressed() {
        showExitAppDialog()
    }
}