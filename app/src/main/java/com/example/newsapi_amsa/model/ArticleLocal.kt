package com.example.newsapi_amsa.model


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "articlesLocal_table")
data class ArticleLocal(
    @SerializedName("author")
    var author: String,
    @SerializedName("content")
    var content: String,
    @SerializedName("description")
    var description: String,
    @SerializedName("publishedAt")
    var publishedAt: String,
    @SerializedName("source")
    var source: Source,
    @SerializedName("title")
    var title: String,
    @SerializedName("url")
    var url: String,
    @SerializedName("urlToImage")
    var urlToImage: String
) {
    @PrimaryKey
    @SerializedName("id")
    var id: Long = 0
}