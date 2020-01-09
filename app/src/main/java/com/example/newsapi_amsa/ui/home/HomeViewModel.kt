package com.example.newsapi_amsa.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.newsapi_amsa.repositories.NewsRepository
import kotlinx.coroutines.Dispatchers

class HomeViewModel : ViewModel() {

    val repository: NewsRepository = NewsRepository()

    val news = liveData(Dispatchers.IO) {
        val retrivedNews = repository.getNews("pl")

        emit(retrivedNews.articles)
    }
}