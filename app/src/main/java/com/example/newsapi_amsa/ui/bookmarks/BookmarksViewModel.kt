package com.example.newsapi_amsa.ui.bookmarks

import android.app.Application
import androidx.lifecycle.*
import com.example.newsapi_amsa.model.Article
import com.example.newsapi_amsa.model.database.NewsDatabase
import com.example.newsapi_amsa.repositories.NewsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class BookmarksViewModel(application: Application): AndroidViewModel(application), CoroutineScope {
    private val job = Job()
    private val repository: NewsRepository

    init {
        val newsDao = NewsDatabase.getInstance(application)?.newsDao()
        repository = NewsRepository(newsDao!!)
    }

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO

    fun insertNews(article: Article) = launch{
        repository.insertNews(article)
    }

    fun removeNews(article: Article) = launch{
        repository.removeNews(article)
    }

    fun getLocalNews(id: Long) = launch{
        repository.getLocalNews(id)
    }

    fun getAllLocalNews(): LiveData<List<Article>> {
        return repository.getAllLocalNews()
    }
}