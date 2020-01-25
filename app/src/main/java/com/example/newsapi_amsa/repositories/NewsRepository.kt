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
import com.example.newsapi_amsa.utils.Utils.Companion.bookmarkRemoteNews
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
            var response = client.getNews(country)
            val localNews = newsDao.getAllBookmarkedNews()
            response.articles = bookmarkRemoteNews(localNews, response.articles)

            return responseHandler.handleSuccess(response)
        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }

    suspend fun getQueryNews(data: HashMap<String, String>): Resource<News> {
        return try {
            val response = client.getQueryNews(data)
            val localNews = newsDao.getAllBookmarkedNews()
            response.articles = bookmarkRemoteNews(localNews, response.articles)

            return responseHandler.handleSuccess(response)
        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }

    suspend fun insertNews(article: Article) = CoroutineScope(Dispatchers.IO).launch {
        newsDao.insertNews(article)
    }

    suspend fun deleteNews(article: Article) = CoroutineScope(Dispatchers.IO).launch {
        newsDao.deleteNews(article.title, article.url, article.description, article.publishedAt)
    }

    suspend fun deleteAllNews() = CoroutineScope(Dispatchers.IO).launch {
        newsDao.deleteAllNews()
    }

    suspend fun getLocalNews(id: Long): Article {
        return newsDao.getNews(id)
    }

    fun getAllLocalNews(): List<Article> {
        return newsDao.getAllBookmarkedNews()
    }
}