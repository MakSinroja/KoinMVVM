package com.example.koinmvvm.preferences

import android.content.Context
import com.example.koinmvvm.constants.DEVICE_UNIQUE_ID
import com.example.koinmvvm.constants.THEME_ID
import com.example.koinmvvm.constants.THEME_LIGHT
import com.example.koinmvvm.constants.USER_TOKEN

class CommonPreferences(context: Context) {
    var themeId: String by PreferenceExtensions(context, THEME_ID, THEME_LIGHT)
    var token: String by PreferenceExtensions(context, USER_TOKEN, "")
    var deviceUniqueId: String by PreferenceExtensions(context, DEVICE_UNIQUE_ID, "")
}