package com.example.newsapi_amsa.ui.search

import android.app.Application
import androidx.lifecycle.*
import com.example.newsapi_amsa.model.Article
import com.example.newsapi_amsa.model.News
import com.example.newsapi_amsa.repositories.NewsRepository
import com.example.newsapi_amsa.utils.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class SearchViewModel(application: Application): AndroidViewModel(application), CoroutineScope {
    private val job = Job()
    private val repository: NewsRepository = NewsRepository(application)
    private val reloadTrigger = MutableLiveData<Boolean>()
    private var queryData: HashMap<String, String> = HashMap()

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO

    private var news: LiveData<Resource<News>> = Transformations.switchMap(reloadTrigger) {
        liveData(Dispatchers.IO) {
            emit(Resource.loading(null))
            emit(repository.getQueryNews(queryData))
        }
    }

    fun refreshNews(data: HashMap<String, String>) {
        queryData = data
        reloadTrigger.value = true
    }

    fun getNews(): LiveData<Resource<News>> {
        return news
    }

    fun insertNews(article: Article) = launch {
        repository.insertNews(article)
    }

    fun removeNews(article: Article) = launch {
        repository.deleteNews(article)
    }
}