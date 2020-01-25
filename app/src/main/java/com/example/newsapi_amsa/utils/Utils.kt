package com.example.newsapi_amsa.utils

import com.example.newsapi_amsa.model.Article
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class Utils {
    companion object {
        fun formatDate(dateString: String): String {
            var newDate: String = ""
            val dateFormat = SimpleDateFormat("d LLLL YYYY, H:mm", Locale("pl", "PL"))
            try {
                val date: Date? = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(dateString)
                newDate = dateFormat.format(date!!)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return newDate
        }

        fun getDaysAgo(daysAgo: Int): String {
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.DAY_OF_YEAR, -daysAgo)

            val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
            return formatter.format(calendar.time)
        }

        fun getHoursAgo(hoursAgo: Int): String {
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.HOUR_OF_DAY, -hoursAgo)

            val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
            return formatter.format(calendar.time)
        }

        fun objectsEqual(a1: Article, a2: Article): Boolean {
            return (a1.publishedAt == a2.publishedAt
                    && a1.source.name == a2.source.name
                    && a1.url == a1.url
                    && a1.title == a2.title)
        }
    }
}