package com.example.newsapi_amsa.repositories

import com.example.newsapi_amsa.model.News
import com.example.newsapi_amsa.model.api.NewsAPI
import com.example.newsapi_amsa.model.api.NewsInterface
import com.example.newsapi_amsa.utils.Resource
import com.example.newsapi_amsa.utils.ResponseHandler


class NewsRepository {
    private var client: NewsInterface = NewsAPI.webservice
    private val responseHandler: ResponseHandler = ResponseHandler()

    suspend fun getNews(country: String): Resource<News> {
        return try {
            val response = client.getNews(country)
            return responseHandler.handleSuccess(response)
        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }
}