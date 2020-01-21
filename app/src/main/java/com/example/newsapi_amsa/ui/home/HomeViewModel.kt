package com.example.newsapi_amsa.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.newsapi_amsa.model.News
import com.example.newsapi_amsa.repositories.NewsRepository
import com.example.newsapi_amsa.utils.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

class HomeViewModel : ViewModel(), CoroutineScope {
    private val job = Job()
    private val repository: NewsRepository = NewsRepository()

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO

    val news: LiveData<Resource<News>> = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        emit(repository.getNews("pl"))
    }
}