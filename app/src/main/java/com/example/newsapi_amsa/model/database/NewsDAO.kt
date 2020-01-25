package com.example.newsapi_amsa.model.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.newsapi_amsa.model.Article

@Dao
interface NewsDAO {
    @Query("SELECT * FROM articles_table WHERE bookmark = 1 ORDER BY id DESC")
    fun getAllBookmarkedNews(): LiveData<List<Article>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNews(article: Article)

    @Update
    suspend fun updateNews(article: Article)

    @Query("SELECT * FROM articles_table WHERE id=:id")
    suspend fun getNews(id: Long): Article

    @Query("DELETE FROM articles_table WHERE title=:title AND publishedAt=:publishedAt AND bookmark=:bookmark")
    suspend fun deleteNews(title: String, publishedAt: String, bookmark: Int)
    @Query("DELETE FROM articles_table")
    suspend fun deleteAllNews()
}