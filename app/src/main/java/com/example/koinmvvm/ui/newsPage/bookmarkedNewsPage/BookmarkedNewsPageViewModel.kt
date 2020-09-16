package com.example.koinmvvm.ui.newsPage.bookmarkedNewsPage

import android.app.Application
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.example.koinmvvm.R
import com.example.koinmvvm.base.BaseViewModel
import com.example.koinmvvm.constants.SOMETHING_WENT_WRONG_ERROR
import com.example.koinmvvm.database.entities.Watcher
import com.example.koinmvvm.database.entities.articles.ArticlesEntity
import com.example.koinmvvm.database.repositories.articles.ArticlesRepository
import com.example.koinmvvm.listeners.NewsArticleListeners
import com.example.koinmvvm.models.articles.Articles
import com.example.koinmvvm.utils.dateTimeFormatUtils.convertDateTimeLocalToUTC
import java.util.*

class BookmarkedNewsPageViewModel constructor(
    application: Application,
    private val articlesRepository: ArticlesRepository
) : BaseViewModel(application), NewsArticleListeners {

    lateinit var bookmarkedNewsPageActivity: BookmarkedNewsPageActivity

    var isDataAvailable = MutableLiveData<Boolean>()

    var articleList = mutableListOf<ArticlesEntity>()

    lateinit var bookmarkedNewsPageAdapter: BookmarkedNewsPageAdapter

    init {
        isDataAvailable.value = false
    }

    override fun initialization() {
        fetchBookmarkedNews()
    }

    override fun snackBarMessagesObserver() {
        bookmarkedNewsPageActivity.apply {
            failureMessage.observe(bookmarkedNewsPageActivity, {
                onFailure(it)
            })

            successMessage.observe(bookmarkedNewsPageActivity, {
                onSuccess(it)
            })

            warningMessage.observe(bookmarkedNewsPageActivity, {
                onWarning(it)
            })
        }
    }

    override fun removeSnackBarMessagesObserver() {
        failureMessage.removeObservers(bookmarkedNewsPageActivity)
        successMessage.removeObservers(bookmarkedNewsPageActivity)
        warningMessage.removeObservers(bookmarkedNewsPageActivity)
    }

    fun onBackPressed(view: View) {
        (view.context as BookmarkedNewsPageActivity).onBackPressed()
    }

    private fun fetchBookmarkedNews() {
        articlesRepository.getBookmarkArticles()
            .observe(bookmarkedNewsPageActivity, { articleMutableList ->
                isDataAvailable.value = articleMutableList.isNotEmpty()

                if (articleMutableList.isNotEmpty()) {
                    articleList = articleMutableList
                    setupPageAdapter()
                }
            })
    }

    private fun setupPageAdapter() {
        bookmarkedNewsPageAdapter = BookmarkedNewsPageAdapter(bookmarkedNewsPageActivity)
        bookmarkedNewsPageAdapter.articleList = articleList
        bookmarkedNewsPageAdapter.newsArticleListeners = this@BookmarkedNewsPageViewModel

        bookmarkedNewsPageActivity.getViewModelDataBinding().apply {
            viewPagerLayout.adapter = bookmarkedNewsPageAdapter
        }
    }

    override fun isFavouriteArticle(
        articles: Articles?,
        articlesEntity: ArticlesEntity?,
        isFavourite: Boolean
    ) {
        if (isFavourite) {
            articlesEntity?.let { entity ->
                entity.watcher = Watcher(deletedAt = convertDateTimeLocalToUTC(Date()))
                val isDeleted = articlesRepository.deleteArticles(entity)

                if (isDeleted == 1) successMessage.value =
                    bookmarkedNewsPageActivity.resources.getString(R.string.str_article_bookmarked_remove)
                else failureMessage.value = SOMETHING_WENT_WRONG_ERROR
            }
            bookmarkedNewsPageAdapter.notifyDataSetChanged()
        }
    }

    override fun showNextArticleStory(position: Int) {
        bookmarkedNewsPageActivity.getViewModelDataBinding().apply {
            viewPagerLayout.currentItem = position
        }
    }
}