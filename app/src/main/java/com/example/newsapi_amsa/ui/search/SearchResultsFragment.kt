package com.example.newsapi_amsa.ui.search

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
import com.example.newsapi_amsa.model.News
import com.example.newsapi_amsa.ui.DisplayNewsFragment
import com.example.newsapi_amsa.utils.*

class SearchResultsFragment(val data: HashMap<String, String>) : Fragment() {

    private lateinit var searchViewModel: SearchViewModel
    lateinit var thisPageAdapter: HomePageAdapter

    private lateinit var recyclerView: RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    private val BACK_STACK_ROOT_TAG = "root_fragment"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_search_results, container, false)

        recyclerView = root.findViewById(R.id.search_articles_recyclerView)
        swipeRefreshLayout = root.findViewById(R.id.swipe_to_refresh_search_results)

        recyclerLoad()

        searchViewModel = ViewModelProviders.of(activity!!).get(SearchViewModel::class.java)

        fetchNews()

        return root
    }

    private fun recyclerLoad() {
        swipeRefreshLayout.setOnRefreshListener {
            searchViewModel.refreshNews(data)
        }

        thisPageAdapter = HomePageAdapter (
            {
                Log.d("Message", "Article item: $it")

                childFragmentManager.beginTransaction()
                    .replace(R.id.search_results_fragment_container, DisplayNewsFragment(it, false))
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .addToBackStack(BACK_STACK_ROOT_TAG)
                    .commit()

                Log.d("item", "Article: ${it.title}")
            },
            {
                when(it.bookmark) {
                    0 -> {
                        Log.d("Message", "Article item: ${it.id}")
                        it.bookmark = 1

                        searchViewModel.insertNews(it)
                    }
                    1 -> {
                        Log.d("Message", "Article item: ${it.id}")
                        it.bookmark = 0
                        searchViewModel.removeNews(it)
                    }
                }
            }
        )

        recyclerView.apply{
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL,false)
            itemAnimator = DefaultItemAnimator()
            adapter = thisPageAdapter
        }
    }

    private fun fetchNews() {
        swipeRefreshLayout.isRefreshing = true

        searchViewModel.getNews().observe(viewLifecycleOwner, Observer<Resource<News>> {
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
}