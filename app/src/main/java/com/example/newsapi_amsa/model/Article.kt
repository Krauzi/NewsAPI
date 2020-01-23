package com.example.newsapi_amsa.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "articles_table")
data class Article(
    @SerializedName("author")
    var author: String = "",
    @SerializedName("content")
    var content: String = "",
    @SerializedName("description")
    var description: String = "",
    @SerializedName("publishedAt")
    var publishedAt: String = "",
    @SerializedName("source")
    @Embedded(prefix = "source_")
    var source: Source,
    @SerializedName("title")
    var title: String = "",
    @SerializedName("url")
    var url: String = "",
    @SerializedName("urlToImage")
    var urlToImage: String = "",
    var inDataBase: Boolean = false,
    var bookmark: Int = 0
) {
    @PrimaryKey(autoGenerate = true)
    @SerializedName("article_id")
    var id: Long = 0
}