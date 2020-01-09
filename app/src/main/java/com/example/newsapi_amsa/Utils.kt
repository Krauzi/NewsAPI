package com.example.newsapi_amsa

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
    }
}