package com.example.newsapi_amsa.ui.bookmarks

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
import com.example.newsapi_amsa.adapters.BookmarksAdapter
import com.example.newsapi_amsa.model.Article
import com.example.newsapi_amsa.ui.DisplayNewsFragment

class BookmarksFragment : Fragment() {

    private lateinit var bookmarksViewModel: BookmarksViewModel
    lateinit var thisPageAdapter: BookmarksAdapter
    private lateinit var recyclerView: RecyclerView

    private val BACK_STACK_ROOT_TAG = "root_fragment"


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_bookmarks, container, false)

        bookmarksViewModel = ViewModelProviders.of(this).get(BookmarksViewModel::class.java)

        recyclerView = root.findViewById(R.id.room_articles_recyclerView)
        recyclerLoad()

        bookmarksViewModel.getAllLocalNews().observe(this, Observer<List<Article>> {
                t -> thisPageAdapter.setArticles(t!!)
        })
        return root
    }
    private fun recyclerLoad() {
        thisPageAdapter = BookmarksAdapter {
            childFragmentManager.beginTransaction()
                .replace(R.id.bookmarks_fragment_container, DisplayNewsFragment(it, true))
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(BACK_STACK_ROOT_TAG)
                .commit()

            Log.d("item", "Article: ${it.title}")
        }

        recyclerView.apply{
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL,false)
            itemAnimator = DefaultItemAnimator()
            adapter = thisPageAdapter
        }
    }
}