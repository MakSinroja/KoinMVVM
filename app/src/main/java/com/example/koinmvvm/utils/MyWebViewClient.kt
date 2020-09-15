package com.example.koinmvvm.utils

import android.view.View
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.example.koinmvvm.base.BaseActivity

class MyWebViewClient constructor(private val activity: AppCompatActivity?) : WebViewClient() {

    lateinit var progressBar: ProgressBar

    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        super.shouldOverrideUrlLoading(view, request)
        progressBar.visibility = View.VISIBLE
        val url: String = request?.url.toString()
        view?.loadUrl(url)
        return true
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
        progressBar.visibility = View.GONE
    }

    override fun onReceivedError(
        view: WebView?,
        request: WebResourceRequest?,
        error: WebResourceError?
    ) {
        super.onReceivedError(view, request, error)
        progressBar.visibility = View.GONE
        activity?.let {
            (it as BaseActivity<*, *>).onFailure("Got Error $error")
        }
    }
}