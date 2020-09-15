package com.example.koinmvvm.api

import com.example.koinmvvm.api.constants.TOP_HEADLINES_API_URL
import com.example.koinmvvm.api.constants.countryCode
import com.example.koinmvvm.base.BaseResponse
import com.example.koinmvvm.models.articles.Articles
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface APIUrls {

    @GET(TOP_HEADLINES_API_URL)
    fun getTopHeadLinesNews(
        @Query("country") country: String = countryCode,
        @Query("pageSize") pageSize: Int = 20,
        @Query("page") page: Int = 0
    ): Observable<Response<BaseResponse<MutableList<Articles>>>>
}