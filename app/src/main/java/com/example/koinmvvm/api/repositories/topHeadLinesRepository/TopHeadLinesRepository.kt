package com.example.koinmvvm.api.repositories.topHeadLinesRepository

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.koinmvvm.api.APIUrls
import com.example.koinmvvm.api.constants.BAD_REQUEST_ERROR
import com.example.koinmvvm.api.constants.SERVER_ERROR
import com.example.koinmvvm.api.constants.TOO_MANY_REQUEST_ERROR
import com.example.koinmvvm.api.constants.UNAUTHORIZED_ACCESS_ERROR
import com.example.koinmvvm.base.BaseResource
import com.example.koinmvvm.base.BaseResponse
import com.example.koinmvvm.constants.SOMETHING_WENT_WRONG_ERROR
import com.example.koinmvvm.models.articles.Articles
import com.example.koinmvvm.utils.enums.ApiResponseStatusCode
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class TopHeadLinesRepository constructor(private val apiUrls: APIUrls) {

    @SuppressLint("CheckResult")
    fun getTopHeadLinesNews(context: Context): MutableLiveData<BaseResource<MutableList<Articles>>> {
        val liveData = MutableLiveData<BaseResource<MutableList<Articles>>>()
        liveData.postValue(BaseResource.loading())

        apiUrls.getTopHeadLinesNews()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ serverResponse ->
                when (serverResponse.code()) {
                    ApiResponseStatusCode.SUCCESS.statusCode -> {
                        val baseResponse = serverResponse.body() as BaseResponse

                        if (baseResponse.status == "ok") {
                            liveData.postValue(
                                BaseResource.success(
                                    baseResponse.articles,
                                    baseResponse.message ?: ""
                                )
                            )
                        } else liveData.postValue(BaseResource.error(baseResponse.message))
                    }
                    ApiResponseStatusCode.BAD_REQUEST.statusCode -> liveData.postValue(
                        BaseResource.badRequest(BAD_REQUEST_ERROR)
                    )
                    ApiResponseStatusCode.UNAUTHORIZED_ACCESS.statusCode -> liveData.postValue(
                        BaseResource.unauthorizedAccess(UNAUTHORIZED_ACCESS_ERROR)
                    )
                    ApiResponseStatusCode.TOO_MANY_REQUEST.statusCode -> liveData.postValue(
                        BaseResource.tooManyRequest(TOO_MANY_REQUEST_ERROR)
                    )
                    ApiResponseStatusCode.SERVER_ERROR.statusCode -> liveData.postValue(
                        BaseResource.serverError(SERVER_ERROR)
                    )
                }
            }, { exception ->
                liveData.postValue(BaseResource.error(SOMETHING_WENT_WRONG_ERROR))
                Timber.d("Top HeadLines Repository Exception :: ${exception.message}")
            })
        return liveData
    }
}