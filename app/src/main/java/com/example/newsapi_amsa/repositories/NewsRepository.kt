package com.example.newsapi_amsa.repositories

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.newsapi_amsa.model.database.NewsDAO
import com.example.newsapi_amsa.model.Article
import com.example.newsapi_amsa.model.News
import com.example.newsapi_amsa.model.api.NewsAPI
import com.example.newsapi_amsa.model.api.NewsInterface
import com.example.newsapi_amsa.model.database.NewsDatabase
import com.example.newsapi_amsa.utils.*
import kotlinx.coroutines.*


class NewsRepository(application: Application) {
    private var newsDao: NewsDAO
    private var client: NewsInterface = NewsAPI.webservice
    private val responseHandler: ResponseHandler = ResponseHandler()

    init {
        val newsDB: NewsDatabase = NewsDatabase.getInstance(application.applicationContext)!!
        newsDao = newsDB.newsDao()
    }

    suspend fun getNews(country: String): Resource<News> {
        return try {
            val response = client.getNews(country)
            return responseHandler.handleSuccess(response)
        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }

    suspend fun insertNews(article: Article) = CoroutineScope(Dispatchers.IO).launch {
        newsDao.insertNews(article)
    }

    suspend fun removeNews(article: Article) = CoroutineScope(Dispatchers.IO).launch {
        newsDao.deleteNews(article.id)
    }

    suspend fun removeAllNews() = CoroutineScope(Dispatchers.IO).launch {
        newsDao.deleteAllNews()
    }

    fun getLocalNews(id: Long): Article {
        return newsDao.getNews(id)
    }

    fun getAllLocalNews(): LiveData<List<Article>> {
        return newsDao.getAllBookmarkedNews()
    }
}