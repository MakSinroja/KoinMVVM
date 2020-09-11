package com.example.koinmvvm.base

import com.google.gson.annotations.SerializedName

data class BaseResponse<T>(
    @SerializedName("status")
    var status: String,
    @SerializedName("code")
    var code: String?,
    @SerializedName("totalResults")
    var totalResult: Int = 0,
    @SerializedName("articles")
    var articles: T?,
    @SerializedName("message")
    var message: String?
)