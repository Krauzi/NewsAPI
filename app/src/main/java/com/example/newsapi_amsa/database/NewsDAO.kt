package com.example.newsapi_amsa.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.newsapi_amsa.model.Article

@Dao
interface NewsDAO {
    @Query("SELECT * FROM articles_table WHERE bookmark = 1")
    fun getAllBookmarkedNews(): LiveData<List<Article>>

    @Insert
    suspend fun insertNews(a: Article)

    @Update
    suspend fun updateNews(a: Article)

    @Delete
    suspend fun deleteNews(a: Article)

    @Query("DELETE FROM articles_table")
    suspend fun deleteAllNews()
}