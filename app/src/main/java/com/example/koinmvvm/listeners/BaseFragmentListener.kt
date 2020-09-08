package com.example.koinmvvm.listeners

interface BaseFragmentListener {
    fun onFragmentAttached()
    fun onFragmentDetached(tag: String)
}