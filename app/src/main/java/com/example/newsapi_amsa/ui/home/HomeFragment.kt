package com.example.newsapi_amsa.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.newsapi_amsa.R
import com.example.newsapi_amsa.adapters.HomePageAdapter
import com.example.newsapi_amsa.model.Article
import com.example.newsapi_amsa.model.News
import com.example.newsapi_amsa.ui.DisplayNewsFragment
import com.example.newsapi_amsa.utils.Resource
import com.example.newsapi_amsa.utils.Status


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    lateinit var thisPageAdapter: HomePageAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    private val BACK_STACK_ROOT_TAG = "root_fragment"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        recyclerView = root.findViewById(R.id.articles_recyclerView)
        swipeRefreshLayout = root.findViewById(R.id.swipe_to_refresh_home)

        recyclerLoad()

        homeViewModel = ViewModelProviders.of(activity!!).get(HomeViewModel::class.java)

        setUpUi()
        fetchNews()

        return root
    }

    private fun recyclerLoad() {

        thisPageAdapter = HomePageAdapter ({
            childFragmentManager.beginTransaction()
                .replace(R.id.home_fragment_container, DisplayNewsFragment(it, false))
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(BACK_STACK_ROOT_TAG)
                .commit()

            Log.d("item", "Article: ${it.title}")
        }, {
            when(it.bookmark) {
                0 -> {
                    it.bookmark = 1
                    homeViewModel.insertNews(it)
                }
                1 -> {
                    it.bookmark = 0
                    homeViewModel.removeNews(it)
                }
            }
        })

        recyclerView.apply{
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL,false)
            itemAnimator = DefaultItemAnimator()
            adapter = thisPageAdapter
        }
    }

    private fun fetchNews() {
        swipeRefreshLayout.isRefreshing = true
        homeViewModel.getNews().observe(this, Observer<Resource<News>> {
            displayNews(it)
        })
    }

    private fun displayNews(it: Resource<News>) {
        swipeRefreshLayout.isRefreshing = false

        when (it.status) {
            Status.SUCCESS -> thisPageAdapter.setArticles(it.data!!.articles)
            Status.LOADING -> Log.d("Message", "Loading message.")
            Status.ERROR -> {
                Log.d("ERROR", "NETWORK ERROR MESSAGE:"+ it.message)
                thisPageAdapter.setArticles(mutableListOf())
            }
        }
    }

    private fun setUpUi() {
        swipeRefreshLayout.setOnRefreshListener {
            homeViewModel.refreshNews()
        }
    }
}