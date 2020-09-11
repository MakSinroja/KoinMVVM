package com.example.koinmvvm.utils.dateTimeFormatUtils

import android.annotation.SuppressLint
import com.example.koinmvvm.constants.OUTPUT_DATE_TIME_FORMAT
import timber.log.Timber
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
fun convertDateToString(date: Date): String {
    var result: String
    val outputFormat = SimpleDateFormat(OUTPUT_DATE_TIME_FORMAT)
    try {
        result = outputFormat.format(date)
    } catch (parseException: ParseException) {
        Timber.d(parseException)
        result = outputFormat.format(Date())
    }
    return result
}