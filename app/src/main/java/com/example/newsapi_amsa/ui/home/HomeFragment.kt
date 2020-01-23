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
import com.example.newsapi_amsa.R
import com.example.newsapi_amsa.adapters.HomePageAdapter
import com.example.newsapi_amsa.model.News
import com.example.newsapi_amsa.ui.DisplayNewsFragment
import com.example.newsapi_amsa.utils.Resource
import com.example.newsapi_amsa.utils.Status


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    lateinit var thisPageAdapter: HomePageAdapter
    private lateinit var recyclerView: RecyclerView

    private val BACK_STACK_ROOT_TAG = "root_fragment"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        recyclerView = root.findViewById(R.id.articles_recyclerView)

        recyclerLoad()

        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        homeViewModel.news.observe(this, Observer<Resource<News>> {
            when (it.status) {
                Status.SUCCESS -> thisPageAdapter.setArticles(it.data!!.articles)
                Status.ERROR -> thisPageAdapter.setArticles(mutableListOf())
            }
        })

        return root
    }

    private fun recyclerLoad() {

        thisPageAdapter = HomePageAdapter ({
            childFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, DisplayNewsFragment(it))
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(BACK_STACK_ROOT_TAG)
                .commit()

            Log.d("item", "Article: ${it.title}")
        }, {
            //ADDING
            if(it.bookmark == 0){
                it.bookmark = 1
                homeViewModel.insertNews(it)
            }
            //DELETING
            else if(it.bookmark == 1){
                it.bookmark = 0
                homeViewModel.removeNews(it)
            }
        })

        recyclerView.apply{
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL,false)
            itemAnimator = DefaultItemAnimator()
            adapter = thisPageAdapter
        }
    }

}