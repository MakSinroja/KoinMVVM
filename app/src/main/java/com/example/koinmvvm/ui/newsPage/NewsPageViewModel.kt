package com.example.koinmvvm.ui.newsPage

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.koinmvvm.api.repositories.topHeadLinesRepository.TopHeadLinesRepository
import com.example.koinmvvm.base.BaseViewModel
import com.example.koinmvvm.models.articles.Articles
import com.example.koinmvvm.utils.enums.Status
import com.example.smileyprogressview.listeners.OnAnimPerformCompletedListener
import com.example.smileyprogressview.ui.SmileyProgressView

class NewsPageViewModel constructor(
    application: Application,
    private val topHeadLinesRepository: TopHeadLinesRepository
) : BaseViewModel(application),
    OnAnimPerformCompletedListener {

    lateinit var newsPageActivity: NewsPageActivity

    lateinit var loadingProgressBarLayout: SmileyProgressView

    var isDataAvailable = MutableLiveData<Boolean>()

    var articleList = mutableListOf<Articles>()

    init {
        isDataAvailable.value = false
    }

    override fun initialization() {
        newsPageActivity.getViewModelDataBinding().apply {
            loadingProgressBarLayout = loadingProgressBar
            loadingProgressBarLayout.setOnAnimPerformCompletedListener(this@NewsPageViewModel)
        }
        fetchTopHeadLinesNews()
    }

    private fun fetchTopHeadLinesNews() {

        topHeadLinesRepository.getTopHeadLinesNews(newsPageActivity)
            .observe(newsPageActivity, {
                when (it.status) {
                    Status.LOADING -> loadingProgressBarLayout.start()
                    Status.SUCCESS -> {
                        isLoading.value = false
                        loadingProgressBarLayout.stop()

                        isDataAvailable.value = it.data?.size != 0
                        articleList = it.data ?: mutableListOf()
                        setupPageAdapter()
                    }
                    Status.BAD_REQUEST,
                    Status.SERVER_ERROR,
                    Status.ERROR -> {
                        loadingProgressBarLayout.stop()
                        failureMessage.value = it.message

                    }
                    Status.UNAUTHORIZED_ACCESS,
                    Status.TOO_MANY_REQUEST -> {
                        loadingProgressBarLayout.stop()
                        warningMessage.value = it.message

                    }
                }
            })
    }

    private fun setupPageAdapter() {
        val newsPageAdapter = NewsPageAdapter(newsPageActivity)
        newsPageAdapter.articleList = articleList

        newsPageActivity.getViewModelDataBinding().apply {
            viewPagerLayout.adapter = newsPageAdapter
        }
    }

    override fun onStart() {
        isLoading.value = true
    }

    override fun onCompleted() {
        isLoading.value = false
    }
}