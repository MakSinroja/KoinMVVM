package com.example.koinmvvm.utils

import android.util.Log
import timber.log.Timber

class CrashReportingTree : Timber.Tree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        if (priority == Log.VERBOSE || priority == Log.DEBUG) return

        if (t != null) {
            if (priority == Log.ERROR) {
                Timber.e(t)
            } else if (priority == Log.WARN) {
                Timber.w(t)
            }
        }
    }
}