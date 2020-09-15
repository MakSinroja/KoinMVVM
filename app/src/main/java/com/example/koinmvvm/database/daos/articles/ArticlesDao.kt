package com.example.koinmvvm.database.daos.articles

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.example.koinmvvm.database.daos.BaseDao
import com.example.koinmvvm.database.entities.articles.ArticlesEntity

@Dao
interface ArticlesDao : BaseDao<ArticlesEntity> {

    @Query("SELECT * FROM articles WHERE title = :articleTitle AND deleted_at IS NULL")
    fun isBookmarkArticle(articleTitle: String): LiveData<ArticlesEntity?>

    @Query("SELECT * FROM articles WHERE deleted_at IS NULL ORDER BY created_at DESC")
    fun getBookmarkArticles(): LiveData<MutableList<ArticlesEntity>>
}