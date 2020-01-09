package com.example.newsapi_amsa.repositories

import com.example.newsapi_amsa.model.api.NewsAPI
import com.example.newsapi_amsa.model.api.NewsInterface

class NewsRepository {
    private var client: NewsInterface = NewsAPI.webservice

    suspend fun getNews(country: String) = client.getNews(country)
}