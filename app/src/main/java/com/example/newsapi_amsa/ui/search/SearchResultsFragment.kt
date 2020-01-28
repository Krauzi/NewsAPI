package com.example.newsapi_amsa.ui.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
import kotlinx.android.synthetic.main.fragment_home.*

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

        thisPageAdapter = HomePageAdapter ({
            childFragmentManager.beginTransaction()
                    .replace(R.id.search_results_fragment_container, DisplayNewsFragment(it, false))
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .addToBackStack(BACK_STACK_ROOT_TAG)
                    .commit()
            },
            {
                when(it.bookmark) {
                    0 -> {
                        it.bookmark = 1
                        searchViewModel.insertNews(it)
                        Toast.makeText(context, R.string.toast_added, Toast.LENGTH_LONG).show()
                    }
                    1 -> {
                        it.bookmark = 0
                        searchViewModel.removeNews(it)
                        Toast.makeText(context, R.string.toast_removed, Toast.LENGTH_LONG).show()
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
            Status.SUCCESS -> {
                error_message.visibility = View.GONE
                thisPageAdapter.setArticles(it.data!!.articles)
            }
            Status.LOADING -> Log.d("Message", "Loading message.")
            Status.ERROR -> {
                thisPageAdapter.setArticles(mutableListOf())
                error_message.visibility = View.VISIBLE
            }
        }
    }
}