package com.example.koinmvvm.listeners

import com.example.koinmvvm.database.entities.articles.ArticlesEntity
import com.example.koinmvvm.models.articles.Articles

interface NewsArticleListeners {
    fun isFavouriteArticle(
        articles: Articles?,
        articlesEntity: ArticlesEntity?,
        isFavourite: Boolean
    )

    fun showNextArticleStory(position: Int)
}