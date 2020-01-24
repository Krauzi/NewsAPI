package com.example.newsapi_amsa.model.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.newsapi_amsa.model.Article

@Dao
interface NewsDAO {
    @Query("SELECT * FROM articles_table WHERE bookmark = 1")
    fun getAllBookmarkedNews(): LiveData<List<Article>>

    @Insert
    suspend fun insertNews(article: Article)

    @Update
    suspend fun updateNews(article: Article)

    @Query("SELECT * FROM articles_table WHERE id=:id")
    suspend fun getNews(id: Long): Article

    @Delete
    suspend fun deleteNews(article: Article)

    @Query("DELETE FROM articles_table")
    suspend fun deleteAllNews()
}