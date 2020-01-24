package com.example.newsapi_amsa.model.api

import com.example.newsapi_amsa.model.News
import retrofit2.http.GET
import retrofit2.http.Query

private const val API_KEY = "x"

interface NewsInterface {
    @GET("top-headlines")
    suspend fun getNews(
        @Query("country") country: String?,
        @Query("apiKey") apiKey: String? = API_KEY
    ): News
}
