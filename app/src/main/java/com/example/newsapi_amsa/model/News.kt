package com.example.newsapi_amsa.model


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "news_table")
data class News(
    @SerializedName("articles")
    var articles: List<Article>,
    @SerializedName("status")
    var status: String = "",
    @SerializedName("totalResults")
    var totalResults: Int = 0
) {
    @PrimaryKey
    @SerializedName("id")
    var id: Long = 0
}