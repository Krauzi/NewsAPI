package com.example.newsapi_amsa

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.newsapi_amsa.adapters.BookmarksAdapter
import com.example.newsapi_amsa.ui.bookmarks.BookmarksViewModel
import com.example.newsapi_amsa.ui.home.HomeViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        // Load news so that home fragment can get them from livedata
        // Fetching new news only on swipe to refresh
        var homeViewModel: HomeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val data: HashMap<String, String> = HashMap()
        data["country"] = "pl"
        homeViewModel.refreshNews(data, true)

        val navController = findNavController(R.id.nav_host_fragment)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_bookmarks, R.id.navigation_search
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }


}
