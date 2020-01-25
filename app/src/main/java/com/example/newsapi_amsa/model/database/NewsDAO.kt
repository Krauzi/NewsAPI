package com.example.newsapi_amsa.model.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.newsapi_amsa.model.Article

@Dao
interface NewsDAO {
    @Query("SELECT * FROM articles_table ORDER BY id DESC")
    fun getAllBookmarkedNews(): List<Article>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNews(article: Article)

    @Update
    suspend fun updateNews(article: Article)

    @Query("SELECT * FROM articles_table WHERE id=:id")
    suspend fun getNews(id: Long): Article

    @Query("DELETE FROM articles_table WHERE title=:title AND url=:url AND description=:description AND publishedAt=:publishedAt")
    suspend fun deleteNews(title: String, url: String, description: String, publishedAt: String)

    @Query("DELETE FROM articles_table")
    suspend fun deleteAllNews()
}