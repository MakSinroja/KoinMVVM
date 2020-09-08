package com.example.koinmvvm.listeners

interface SnackBarMessagesListeners {
    fun onFailure(message: String)
    fun onSuccess(message: String)
    fun onWarning(message: String)
}