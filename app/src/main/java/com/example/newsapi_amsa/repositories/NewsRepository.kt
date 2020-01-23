package com.example.newsapi_amsa.repositories

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.newsapi_amsa.database.NewsDAO
import com.example.newsapi_amsa.database.NewsDatabase
import com.example.newsapi_amsa.model.Article
import com.example.newsapi_amsa.model.News
import com.example.newsapi_amsa.model.api.NewsAPI
import com.example.newsapi_amsa.model.api.NewsInterface
import com.example.newsapi_amsa.utils.Resource
import com.example.newsapi_amsa.utils.ResponseHandler
import kotlinx.coroutines.*


class NewsRepository(application: Application) {
    private var client: NewsInterface = NewsAPI.webservice
    private val responseHandler: ResponseHandler = ResponseHandler()
    private var newsDao: NewsDAO

    init {
        val database: NewsDatabase= NewsDatabase.getInstance(application.applicationContext)!!
        newsDao = database.newsDao()
    }

    suspend fun getNews(country: String): Resource<News> {
        return try {
            val response = client.getNews(country)
            return responseHandler.handleSuccess(response)
        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }

    fun insertNews(a: Article) = CoroutineScope(Dispatchers.IO).launch {
        newsDao.insertNews(a)
    }

    fun removeNews(a: Article) = CoroutineScope(Dispatchers.IO).launch {
        newsDao.deleteNews(a)
    }

    fun getAllBookmarkedNews() = CoroutineScope(Dispatchers.IO).launch {
        newsDao.getAllBookmarkedNews()
    }
}