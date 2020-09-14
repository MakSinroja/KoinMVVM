package com.example.koinmvvm.utils.dateTimeFormatUtils

import android.annotation.SuppressLint
import com.example.koinmvvm.constants.OUTPUT_DATE_TIME_FORMAT
import com.example.koinmvvm.constants.UTC_DATE_TIME_FORMAT
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

@SuppressLint("SimpleDateFormat")
fun convertDateTimeUTCToLocal(date: Date, format: String = UTC_DATE_TIME_FORMAT): Date {
    val simpleDateFormat = SimpleDateFormat(format)
    val utcDateTimeString = simpleDateFormat.format(date)

    val utc = TimeZone.getTimeZone("UTC")
    simpleDateFormat.timeZone = utc
    val calendar = GregorianCalendar(utc)

    calendar.time = simpleDateFormat.parse(utcDateTimeString) ?: Date()
    return calendar.time
}

@SuppressLint("SimpleDateFormat")
fun convertDateTimeLocalToUTC(date: Date, format: String = UTC_DATE_TIME_FORMAT): Date {
    val dateTime = SimpleDateFormat(format, Locale.getDefault())
    val formatDateTime = SimpleDateFormat(format)

    formatDateTime.timeZone = TimeZone.getTimeZone("UTC")
    val utcDateTimeString = formatDateTime.format(date)
    return dateTime.parse(utcDateTimeString) ?: Date()
}

@SuppressLint("SimpleDateFormat")
fun convertDateToDateTimeFormat(date: Date, format: String = UTC_DATE_TIME_FORMAT): Date {
    val dateTime = SimpleDateFormat(format, Locale.getDefault())
    val formatDateTime = SimpleDateFormat(format)

    val utcDateTimeString = formatDateTime.format(date)
    return dateTime.parse(utcDateTimeString) ?: Date()
}