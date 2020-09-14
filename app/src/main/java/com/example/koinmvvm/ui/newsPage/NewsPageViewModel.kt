package com.example.koinmvvm.ui.newsPage

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.koinmvvm.R
import com.example.koinmvvm.api.repositories.topHeadLinesRepository.TopHeadLinesRepository
import com.example.koinmvvm.base.BaseViewModel
import com.example.koinmvvm.constants.SOMETHING_WENT_WRONG_ERROR
import com.example.koinmvvm.database.entities.Watcher
import com.example.koinmvvm.database.entities.articles.ArticlesEntity
import com.example.koinmvvm.database.repositories.articles.ArticlesRepository
import com.example.koinmvvm.listeners.FavouriteArticleListeners
import com.example.koinmvvm.models.articles.Articles
import com.example.koinmvvm.utils.dateTimeFormatUtils.convertDateTimeLocalToUTC
import com.example.koinmvvm.utils.enums.Status
import com.example.smileyprogressview.listeners.OnAnimPerformCompletedListener
import com.example.smileyprogressview.ui.SmileyProgressView
import com.google.gson.Gson
import java.util.*

class NewsPageViewModel constructor(
    application: Application,
    private val topHeadLinesRepository: TopHeadLinesRepository,
    private val articlesRepository: ArticlesRepository
) : BaseViewModel(application),
    OnAnimPerformCompletedListener, FavouriteArticleListeners {

    lateinit var newsPageActivity: NewsPageActivity

    lateinit var loadingProgressBarLayout: SmileyProgressView

    var isDataAvailable = MutableLiveData<Boolean>()

    var articleList = mutableListOf<Articles>()

    lateinit var newsPageAdapter: NewsPageAdapter

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
        newsPageAdapter = NewsPageAdapter(newsPageActivity)
        newsPageAdapter.articleList = articleList
        newsPageAdapter.favouriteArticleListeners = this@NewsPageViewModel
        newsPageAdapter.articlesRepository = articlesRepository

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

    override fun isFavouriteArticle(
        articles: Articles,
        articlesEntity: ArticlesEntity?,
        isFavourite: Boolean
    ) {
        if (!isFavourite) {
            val randomUUID = UUID.randomUUID().toString()

            val newArticlesEntity =
                Gson().fromJson(Gson().toJson(articles), ArticlesEntity::class.java)

            newArticlesEntity.articleId = randomUUID
            newArticlesEntity.sourceId = articles.source.id
            newArticlesEntity.sourceName = articles.source.name

            newArticlesEntity.watcher = Watcher(
                createdAt = convertDateTimeLocalToUTC(Date()),
                updatedAt = convertDateTimeLocalToUTC(Date())
            )

            val isStored = articlesRepository.insertArticles(newArticlesEntity)

            if (isStored > 0) successMessage.value =
                newsPageActivity.resources.getString(R.string.str_article_bookmarked)
            else failureMessage.value = SOMETHING_WENT_WRONG_ERROR
        } else {
            articlesEntity?.let { entity ->
                entity.watcher = Watcher(deletedAt = convertDateTimeLocalToUTC(Date()))
                val isDeleted = articlesRepository.deleteArticles(entity)

                if (isDeleted == 1) successMessage.value =
                    newsPageActivity.resources.getString(R.string.str_article_bookmarked_remove)
                else failureMessage.value = SOMETHING_WENT_WRONG_ERROR
            }
        }

        newsPageAdapter.notifyDataSetChanged()
    }
}