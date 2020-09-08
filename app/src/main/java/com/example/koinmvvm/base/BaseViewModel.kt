package com.example.koinmvvm.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.koinmvvm.preferences.CommonPreferences

abstract class BaseViewModel(application: Application) : AndroidViewModel(application) {

    var commonPreferences: CommonPreferences = CommonPreferences(application)

    var isLoading = MutableLiveData<Boolean>()

    var failureMessage = MutableLiveData<String>()
    var successMessage = MutableLiveData<String>()
    var warningMessage = MutableLiveData<String>()

    init {
        isLoading.value = false
    }
}