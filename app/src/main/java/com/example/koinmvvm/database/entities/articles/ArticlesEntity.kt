package com.example.koinmvvm.database.entities.articles

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.koinmvvm.database.constants.*
import com.example.koinmvvm.database.entities.Watcher
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = TABLE_ARTICLES)
data class ArticlesEntity(
    @PrimaryKey
    @ColumnInfo(name = FIELD_ARTICLES_ID)
    @SerializedName(FIELD_ARTICLES_ID)
    var articleId: String,
    @ColumnInfo(name = FIELD_ARTICLES_SOURCE_ID)
    @SerializedName("id")
    var sourceId: String? = null,
    @ColumnInfo(name = FIELD_ARTICLES_SOURCE_NAME)
    @SerializedName("name")
    var sourceName: String,
    @ColumnInfo(name = FIELD_ARTICLES_AUTHOR)
    @SerializedName("author")
    var author: String? = null,
    @ColumnInfo(name = FIELD_ARTICLES_TITLE)
    @SerializedName("title")
    var title: String,
    @ColumnInfo(name = FIELD_ARTICLES_DESCRIPTION)
    @SerializedName("description")
    var description: String? = null,
    @ColumnInfo(name = FIELD_ARTICLES_URL)
    @SerializedName("url")
    var url: String,
    @ColumnInfo(name = FIELD_ARTICLES_URL_TO_IMAGE)
    @SerializedName("urlToImage")
    var urlToImage: String,
    @ColumnInfo(name = FIELD_ARTICLES_PUBLISH_AT)
    @SerializedName("publishedAt")
    var publishAt: String,
    @ColumnInfo(name = FIELD_ARTICLES_CONTENT)
    @SerializedName("content")
    var content: String? = null,
    @Embedded
    var watcher: Watcher?
) : Serializable