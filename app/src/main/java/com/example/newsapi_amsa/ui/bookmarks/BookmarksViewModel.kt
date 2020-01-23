package com.example.newsapi_amsa.ui.bookmarks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newsapi_amsa.database.NewsDatabase

class BookmarksViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This is dashboard Fragment"
    }
    val text: LiveData<String> = _text

//    inline fun <T> LiveData<T>.filter(crossinline predicate : (T?)->Boolean): LiveData<T> {
//        val mutableLiveData: MediatorLiveData<T> = MediatorLiveData()
//        mutableLiveData.addSource(this) {
//            if(predicate(it))
//                mutableLiveData.value = it
//        }
//        return mutableLiveData
//    }
}