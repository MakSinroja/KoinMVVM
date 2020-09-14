package com.example.koinmvvm.listeners

import com.example.koinmvvm.database.entities.articles.ArticlesEntity
import com.example.koinmvvm.models.articles.Articles

interface FavouriteArticleListeners {
    fun isFavouriteArticle(
        articles: Articles,
        articlesEntity: ArticlesEntity?,
        isFavourite: Boolean
    )
}