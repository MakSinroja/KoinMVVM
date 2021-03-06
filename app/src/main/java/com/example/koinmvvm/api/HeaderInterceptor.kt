package com.example.koinmvvm.api

import android.content.Context
import com.example.koinmvvm.constants.HEADER_AUTHORIZATION_TOKEN
import com.example.koinmvvm.preferences.CommonPreferences
import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor constructor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val apiKey = CommonPreferences(context).apiKey

        if (apiKey.isNotEmpty()) {
            val request = chain.request().newBuilder()
                .addHeader(HEADER_AUTHORIZATION_TOKEN, apiKey)
                .build()
            return chain.proceed(request)
        }

        return chain.proceed(chain.request())
    }
}