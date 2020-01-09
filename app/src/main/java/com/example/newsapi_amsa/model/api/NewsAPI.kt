package com.example.newsapi_amsa.model.api

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://newsapi.org/v2/"

object NewsAPI {
    val webservice: NewsInterface by lazy {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .baseUrl(BASE_URL)
            .build()
            .create(NewsInterface::class.java)
    }
}