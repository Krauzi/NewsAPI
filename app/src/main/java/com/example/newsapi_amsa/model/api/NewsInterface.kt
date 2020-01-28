package com.example.newsapi_amsa.model.api

import com.example.newsapi_amsa.model.News
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

private const val API_KEY = "x"

interface NewsInterface {
    @GET("top-headlines")
    suspend fun getNews(
        @QueryMap options: HashMap<String, String>,
        @Query("apiKey") apiKey: String? = API_KEY
    ): News

    @GET("everything")
    suspend fun getQueryNews(
        @QueryMap options: HashMap<String, String>,
        @Query("apiKey") apiKey: String? = API_KEY
    ): News
}
