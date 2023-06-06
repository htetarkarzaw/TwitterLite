package com.htetarkarzaw.twitterlite.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

object DateTimeUtil {

    fun convertTimestampToRelativeTime(timestamp: Long): String {
        val currentTime = System.currentTimeMillis()
        val diffInMillis = currentTime - timestamp

        val seconds = TimeUnit.MILLISECONDS.toSeconds(diffInMillis)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(diffInMillis)
        val hours = TimeUnit.MILLISECONDS.toHours(diffInMillis)
        val days = TimeUnit.MILLISECONDS.toDays(diffInMillis)

        return when {
            seconds < 60 -> "$seconds sec"
            minutes < 60 -> "$minutes min"
            hours < 24 -> "$hours h"
            days < 7 -> "$days d"
            days < 30 -> "${days / 7} w"
            days < 365 -> "${days / 30} m"
            else -> "${days / 365} y"
        }
    }

    fun dateFormatForDetail(timestamp: Long): String? {
        val sdf = SimpleDateFormat("hh:mm aa MM/dd/yyyy", Locale.getDefault())
        val date = Date(timestamp)
        return sdf.format(date)
    }
}