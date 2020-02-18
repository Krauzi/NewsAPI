package com.example.newsapi_amsa.model.database

import androidx.room.*
import com.example.newsapi_amsa.model.Article

@Dao
interface NewsDAO {
    @Query("SELECT * FROM articles_table ORDER BY id DESC")
    suspend fun getAllBookmarkedNews(): List<Article>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
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