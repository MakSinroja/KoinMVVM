package com.example.koinmvvm.ui.newsPage

import android.app.Application
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.viewpager.widget.ViewPager
import com.example.koinmvvm.R
import com.example.koinmvvm.api.repositories.topHeadLinesRepository.TopHeadLinesRepository
import com.example.koinmvvm.base.BaseViewModel
import com.example.koinmvvm.constants.SOMETHING_WENT_WRONG_ERROR
import com.example.koinmvvm.constants.THEME_DARK
import com.example.koinmvvm.constants.THEME_LIGHT
import com.example.koinmvvm.database.entities.Watcher
import com.example.koinmvvm.database.entities.articles.ArticlesEntity
import com.example.koinmvvm.database.repositories.articles.ArticlesRepository
import com.example.koinmvvm.listeners.NewsArticleListeners
import com.example.koinmvvm.models.articles.Articles
import com.example.koinmvvm.ui.newsPage.bookmarkedNewsPage.BookmarkedNewsPageActivity
import com.example.koinmvvm.utils.dateTimeFormatUtils.convertDateTimeLocalToUTC
import com.example.koinmvvm.utils.enums.Status
import com.example.smileyprogressview.listeners.OnAnimPerformCompletedListener
import com.example.smileyprogressview.ui.SmileyProgressView
import com.google.gson.Gson
import org.jetbrains.anko.startActivity
import java.util.*

class NewsPageViewModel constructor(
    application: Application,
    private val topHeadLinesRepository: TopHeadLinesRepository,
    private val articlesRepository: ArticlesRepository
) : BaseViewModel(application),
    OnAnimPerformCompletedListener, NewsArticleListeners {

    lateinit var newsPageActivity: NewsPageActivity

    lateinit var loadingProgressBarLayout: SmileyProgressView

    var isDataAvailable = MutableLiveData<Boolean>()

    var articleList = mutableListOf<Articles>()

    lateinit var newsPageAdapter: NewsPageAdapter

    var page: Int = 1
    var totalArticles: Int = 0

    init {
        isDataAvailable.value = true
    }

    override fun initialization() {
        newsPageActivity.getViewModelDataBinding().apply {
            loadingProgressBarLayout = loadingProgressBar
            loadingProgressBarLayout.setOnAnimPerformCompletedListener(this@NewsPageViewModel)
        }
        fetchTopHeadLinesNews()
    }

    private fun fetchTopHeadLinesNews() {
        topHeadLinesRepository.getTopHeadLinesNews(newsPageActivity, page)
            .observe(newsPageActivity, {
                when (it.status) {
                    Status.LOADING -> loadingProgressBarLayout.start()
                    Status.SUCCESS -> {
                        isLoading.value = false
                        loadingProgressBarLayout.stop()

                        if (articleList.isEmpty()) {
                            isDataAvailable.value = it.data?.size != 0
                            articleList = it.data ?: mutableListOf()
                            totalArticles = it.totalResult

                            setupPageAdapter()
                        } else {
                            articleList.addAll(it.data ?: mutableListOf())
                            newsPageAdapter.notifyDataSetChanged()
                        }
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
        newsPageAdapter.newsArticleListeners = this@NewsPageViewModel
        newsPageAdapter.articlesRepository = articlesRepository

        newsPageActivity.getViewModelDataBinding().apply {
            viewPagerLayout.adapter = newsPageAdapter

            viewPagerLayout.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {
                }

                override fun onPageSelected(position: Int) {
                    if (position == (articleList.size - 1) && articleList.size < totalArticles) {
                        page++
                        fetchTopHeadLinesNews()
                    }
                }

                override fun onPageScrollStateChanged(state: Int) {
                }
            })
        }
    }

    override fun onStart() {
        isLoading.value = true
    }

    override fun onCompleted() {
        isLoading.value = false
    }

    override fun isFavouriteArticle(
        articles: Articles?,
        articlesEntity: ArticlesEntity?,
        isFavourite: Boolean
    ) {
        if (!isFavourite) {
            val randomUUID = UUID.randomUUID().toString()

            val newArticlesEntity =
                Gson().fromJson(Gson().toJson(articles), ArticlesEntity::class.java)

            newArticlesEntity.articleId = randomUUID
            articles?.let { article ->
                newArticlesEntity.sourceId = article.source.id
                newArticlesEntity.sourceName = article.source.name
            }

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

    override fun showNextArticleStory(position: Int) {
        newsPageActivity.getViewModelDataBinding().apply {
            viewPagerLayout.currentItem = position
        }
    }

    fun onClickBookmarkedNews(view: View) {
        (view.context as NewsPageActivity).startActivity<BookmarkedNewsPageActivity>()
    }

    fun onClickChangeTheme(view: View) {
        if (commonPreferences.themeId == THEME_LIGHT)
            commonPreferences.themeId = THEME_DARK
        else
            commonPreferences.themeId = THEME_LIGHT

        articleList.clear()
        isDataAvailable.value = true

        newsPageActivity.recreate()
    }
}