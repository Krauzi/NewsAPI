package com.example.newsapi_amsa.ui.bookmarks

import android.app.Application
import androidx.lifecycle.*
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

class BookmarksViewModel(application: Application): AndroidViewModel(application), CoroutineScope {
    private val job = Job()
    private val repository: NewsRepository = NewsRepository(application)
    private val news: LiveData<List<Article>> = liveData(Dispatchers.IO) {
        emit(repository.getAllLocalNews())
    }

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO

    fun deleteNews(article: Article) = launch{
        repository.deleteNews(article)
    }

    suspend fun getLocalNews(id: Long) = launch{
        repository.getLocalNews(id)
    }

    fun getAllLocalNews(): LiveData<List<Article>> {
        return news
    }
}