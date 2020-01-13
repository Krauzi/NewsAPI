package com.example.newsapi_amsa.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.newsapi_amsa.model.News
import com.example.newsapi_amsa.repositories.NewsRepository
import com.example.newsapi_amsa.utils.Resource
import kotlinx.coroutines.Dispatchers

class HomeViewModel : ViewModel() {

    val repository: NewsRepository = NewsRepository()

    val news: LiveData<Resource<News>> = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        emit(repository.getNews("pl"))
    }
}