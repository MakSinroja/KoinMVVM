package com.example.koinmvvm.ui.newsPage.newsWebView

import android.annotation.SuppressLint
import android.app.Application
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import com.example.koinmvvm.base.BaseViewModel
import com.example.koinmvvm.utils.MyWebViewClient

class NewsWebViewViewModel constructor(application: Application) : BaseViewModel(application) {

    lateinit var newsWebViewActivity: NewsWebViewActivity

    lateinit var myWebViewClient: MyWebViewClient

    override fun initialization() {
        myWebViewClient = MyWebViewClient(newsWebViewActivity)
    }

    fun onBackPressed(view: View) {
        (view.context as NewsWebViewActivity).onBackPressed()
    }

    @SuppressLint("SetJavaScriptEnabled")
    fun setupWebView() {
        newsWebViewActivity.getViewModelDataBinding().apply {
            myWebViewClient.progressBar = progressBarHorizontal

            articleTitleTextView.text = newsWebViewActivity.articleTitle
            articleTitleTextView.isSelected = true

            newsWebView.webChromeClient = object : WebChromeClient() {
                override fun onProgressChanged(view: WebView?, newProgress: Int) {
                    super.onProgressChanged(view, newProgress)
                    progressBarHorizontal.let {
                        it.progress = newProgress
                        if (it.progress == 100)
                            it.visibility = View.GONE
                    }
                }
            }

            newsWebView.webViewClient = myWebViewClient
            newsWebView.settings.javaScriptEnabled = true
            newsWebView.settings.builtInZoomControls = false
            newsWebView.settings.displayZoomControls = true

            newsWebView.loadUrl(newsWebViewActivity.articleWebURL)
        }
    }
}