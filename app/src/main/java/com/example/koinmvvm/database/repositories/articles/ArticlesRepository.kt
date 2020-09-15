package com.example.koinmvvm.database.repositories.articles

import androidx.lifecycle.LiveData
import com.example.koinmvvm.database.daos.articles.ArticlesDao
import com.example.koinmvvm.database.entities.articles.ArticlesEntity

class ArticlesRepository(private val articlesDao: ArticlesDao) {

    fun isBookmarkArticle(articleTitle: String): LiveData<ArticlesEntity?> {
        return articlesDao.isBookmarkArticle(articleTitle)
    }

    fun insertArticles(articlesEntity: ArticlesEntity): Long {
        return articlesDao.insert(articlesEntity)
    }

    fun deleteArticles(articlesEntity: ArticlesEntity): Int {
        return articlesDao.update(articlesEntity)
    }

    fun getBookmarkArticles(): LiveData<MutableList<ArticlesEntity>> {
        return articlesDao.getBookmarkArticles()
    }
}