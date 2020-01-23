package com.example.newsapi_amsa.model

import com.google.gson.annotations.SerializedName

data class Source(
    @SerializedName("id")
    var id: Long,
    @SerializedName("name")
    var name: String = ""
)