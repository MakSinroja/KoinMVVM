package com.example.koinmvvm.listeners

interface BaseFragmentListeners {
    fun onFragmentAttached()
    fun onFragmentDetached(tag: String)
}