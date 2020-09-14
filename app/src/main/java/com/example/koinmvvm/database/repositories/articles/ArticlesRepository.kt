package com.example.koinmvvm.database.repositories.articles

import com.example.koinmvvm.database.daos.articles.ArticlesDao
import com.example.koinmvvm.database.entities.articles.ArticlesEntity

class ArticlesRepository(private val articlesDao: ArticlesDao) {

    fun isFavouriteArticle(articleTitle: String): ArticlesEntity? {
        return articlesDao.isFavouriteArticle(articleTitle)
    }

    fun insertArticles(articlesEntity: ArticlesEntity): Long {
        return articlesDao.insert(articlesEntity)
    }

    fun deleteArticles(articlesEntity: ArticlesEntity): Int {
        return articlesDao.update(articlesEntity)
    }
}