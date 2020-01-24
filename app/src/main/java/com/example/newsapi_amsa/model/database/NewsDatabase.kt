package com.example.newsapi_amsa.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.newsapi_amsa.model.Article

@Database(entities = [Article::class], version = 3)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsDAO

    companion object {
        private var INSTANCE: NewsDatabase? = null

        fun getInstance(context: Context): NewsDatabase? {
            if (INSTANCE == null) {
                // synchronized - only one thread at the time can access that instance
                synchronized(NewsDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        NewsDatabase::class.java, "news_db").fallbackToDestructiveMigration().build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}