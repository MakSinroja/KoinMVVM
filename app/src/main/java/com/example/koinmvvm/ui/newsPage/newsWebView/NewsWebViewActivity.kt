package com.example.koinmvvm.ui.newsPage.newsWebView

import androidx.lifecycle.LifecycleOwner
import com.example.koinmvvm.BR
import com.example.koinmvvm.R
import com.example.koinmvvm.base.BaseActivity
import com.example.koinmvvm.constants.ARTICLE_TITLE
import com.example.koinmvvm.constants.URL
import com.example.koinmvvm.databinding.ActivityNewsWebViewBinding
import org.koin.android.ext.android.inject

class NewsWebViewActivity : BaseActivity<ActivityNewsWebViewBinding, NewsWebViewViewModel>() {

    private val model: NewsWebViewViewModel by inject()

    override fun fullscreenActivity(): Boolean = false

    override fun transparentActivity(): Boolean = false

    override fun changeStatusBarColor(): Int = R.attr.themeStatusBarColor

    override fun setContentView(): Int = R.layout.activity_news_web_view

    override fun setBindingVariable(): Int = BR.viewModel

    override fun setViewModel(): NewsWebViewViewModel = model

    override fun setLifeCycleOwner(): LifecycleOwner = this@NewsWebViewActivity

    lateinit var articleTitle: String
    lateinit var articleWebURL: String

    override fun initialization() {
        performViewModelVariableBinding()
        setListeners()
        getIntentData()
    }

    override fun onDestroy() {
        super.onDestroy()
        model.removeSnackBarMessagesObserver()
    }

    private fun performViewModelVariableBinding() {
        model.newsWebViewActivity = this@NewsWebViewActivity
        model.initialization()
    }

    private fun setListeners() {
        model.snackBarMessagesObserver()
    }

    private fun getIntentData() {
        if (intent.hasExtra(ARTICLE_TITLE) && intent.hasExtra(URL)) {
            articleTitle = intent.getStringExtra(ARTICLE_TITLE) ?: "No title found!"
            articleWebURL = intent.getStringExtra(URL) ?: "https://www.google.co.in"
            model.setupWebView()
        }
    }
}