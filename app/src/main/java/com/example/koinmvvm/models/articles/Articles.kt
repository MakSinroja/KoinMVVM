package com.example.koinmvvm.models.articles

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

data class Articles(
    @SerializedName("source")
    var source: Source,
    @SerializedName("author")
    var author: String,
    @SerializedName("title")
    var title: String,
    @SerializedName("description")
    var description: String? = null,
    @SerializedName("url")
    var url: String,
    @SerializedName("urlToImage")
    var urlToImage: String,
    @SerializedName("publishedAt")
    var publishAt: Date? = Date(),
    @SerializedName("content")
    var content: String? = null
) : Serializable

data class Source(
    @SerializedName("id")
    var id: String,
    @SerializedName("name")
    var name: String
) : Serializable