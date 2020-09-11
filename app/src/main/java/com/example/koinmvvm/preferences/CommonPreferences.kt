package com.example.koinmvvm.preferences

import android.content.Context
import com.example.koinmvvm.BuildConfig
import com.example.koinmvvm.constants.*

class CommonPreferences(context: Context) {
    var themeId: String by PreferenceExtensions(context, THEME_ID, THEME_LIGHT)
    var token: String by PreferenceExtensions(context, USER_TOKEN, "")
    var apiKey: String by PreferenceExtensions(context, NEWS_API_KEY, BuildConfig.NEWS_API_KEY)
    var deviceUniqueId: String by PreferenceExtensions(context, DEVICE_UNIQUE_ID, "")
}