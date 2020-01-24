package com.example.newsapi_amsa.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.newsapi_amsa.model.Article
import com.example.newsapi_amsa.model.News
import com.example.newsapi_amsa.model.database.NewsDatabase
import com.example.newsapi_amsa.repositories.NewsRepository
import com.example.newsapi_amsa.utils.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class HomeViewModel(application: Application): AndroidViewModel(application), CoroutineScope {
    private val job = Job()
    private val repository: NewsRepository

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO

    init {
        val newsDao = NewsDatabase.getInstance(application)?.newsDao()
        repository = NewsRepository(newsDao!!)
    }

    val news: LiveData<Resource<News>> = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        emit(repository.getNews("pl"))
    }

    fun insertNews(article: Article) = launch {
        repository.insertNews(article)
    }

    fun removeNews(article: Article) = launch {
        repository.removeNews(article)
    }

    fun removeAllNews() = launch {
        repository.removeAllNews()
    }
}