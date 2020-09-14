package com.example.koinmvvm.database

import android.text.TextUtils
import androidx.room.TypeConverter
import com.example.koinmvvm.constants.DATABASE_DATE_PATTERN
import com.example.koinmvvm.constants.UTC_DATE_TIME_FORMAT
import com.example.koinmvvm.utils.dateTimeFormatUtils.convertDateTimeLocalToUTC
import com.example.koinmvvm.utils.dateTimeFormatUtils.convertDateTimeUTCToLocal
import java.text.SimpleDateFormat
import java.util.*

class DateTimeConverter {
    private val dateTime = SimpleDateFormat(UTC_DATE_TIME_FORMAT, Locale.getDefault())
    private val date = SimpleDateFormat(DATABASE_DATE_PATTERN, Locale.getDefault())

    @TypeConverter
    fun forTimestamp(value: String?): Date? {
        return if (!TextUtils.isEmpty(value)) {
            try {
                convertDateTimeUTCToLocal(
                    dateTime.parse(value ?: "") ?: Date()
                )
            } catch (exception: Exception) {
                exception.printStackTrace()
            }

            try {
                convertDateTimeUTCToLocal(
                    convertDateTimeUTCToLocal(
                        this.date.parse(value ?: "") ?: Date()
                    ),
                    DATABASE_DATE_PATTERN
                )
            } catch (exception: Exception) {
                exception.printStackTrace()
            }

            convertDateTimeUTCToLocal(Date(), DATABASE_DATE_PATTERN)
        } else {
            null
        }
    }

    @TypeConverter
    fun dateTimeToTimestamp(date: Date?): String? {
        return if (date == null) null else try {
            val dateTimeString = convertDateTimeLocalToUTC(date)
            return dateTime.format(dateTimeString)
        } catch (exception: Exception) {
            exception.printStackTrace()
            null
        }
    }
}