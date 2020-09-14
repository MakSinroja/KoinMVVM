package com.example.koinmvvm.di.module.api

import com.example.koinmvvm.BuildConfig
import com.example.koinmvvm.api.APIUrls
import com.example.koinmvvm.api.HeaderInterceptor
import com.example.koinmvvm.constants.TIMEOUT
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

val apiModule = module {
    single { GsonBuilder().create() }

    single { HeaderInterceptor(get()) }

    single { GsonConverterFactory.create() }

    single { ScalarsConverterFactory.create() }

    single { RxJava2CallAdapterFactory.createAsync() }

    single {
        val interceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG)
            interceptor.level = HttpLoggingInterceptor.Level.BODY
        else
            interceptor.level = HttpLoggingInterceptor.Level.NONE

        OkHttpClient.Builder().apply {
            readTimeout(TIMEOUT, TimeUnit.SECONDS)
            connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            writeTimeout(TIMEOUT, TimeUnit.SECONDS)
            addInterceptor(get<HeaderInterceptor>())
            addInterceptor(interceptor)
        }.build()
    }

    single {
        Retrofit.Builder().apply {
            addConverterFactory(get<GsonConverterFactory>())
            addConverterFactory(get<ScalarsConverterFactory>())
            addCallAdapterFactory(get<RxJava2CallAdapterFactory>())
            client(get<OkHttpClient>())
            baseUrl(BuildConfig.BASE_API_URL)
        }.build()
    }

    factory { get<Retrofit>().create(APIUrls::class.java) }
}