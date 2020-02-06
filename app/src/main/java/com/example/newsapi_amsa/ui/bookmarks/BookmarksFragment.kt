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
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapi_amsa.R
import com.example.newsapi_amsa.adapters.BookmarksAdapter
import com.example.newsapi_amsa.model.Article
import com.example.newsapi_amsa.ui.DisplayNewsFragment
import com.example.newsapi_amsa.ui.home.HomeViewModel
import com.example.newsapi_amsa.utils.Status
import com.example.newsapi_amsa.utils.SwipeToDeleteCallback
import com.example.newsapi_amsa.utils.Utils.Companion.objectsEqual
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_bookmarks.*


class BookmarksFragment : Fragment() {

    private lateinit var bookmarksViewModel: BookmarksViewModel
    private lateinit var homeViewModel: HomeViewModel
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
        homeViewModel = ViewModelProviders.of(activity!!).get(HomeViewModel::class.java)

        recyclerView = root.findViewById(R.id.room_articles_recyclerView)
        recyclerLoad()

        bookmarksViewModel.getAllLocalNews().observe(viewLifecycleOwner, Observer<List<Article>> {
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
        }

        recyclerView.apply{
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL,false)
            itemAnimator = DefaultItemAnimator()
            adapter = thisPageAdapter
        }

        val swipeHandler = object : SwipeToDeleteCallback(activity!!.applicationContext) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapter = recyclerView.adapter as BookmarksAdapter
                val news = adapter.getArticleAt(viewHolder.adapterPosition)
                var position = viewHolder.adapterPosition
                adapter.removeAt(position)
                bookmarksViewModel.deleteNews(news)

                var response = homeViewModel.getNews().value
                when (response?.status) {
                    Status.SUCCESS -> {
                        var list = response.data!!.articles
                        for (i in response.data!!.articles.indices) {
                            if (objectsEqual(news, list[i])) list[i].bookmark = 0
                        }
                    }
                    else -> {}
                }

                val view: View = bookmarks_fragment_container
                Snackbar.make(view, R.string.snackbar_message, Snackbar.LENGTH_LONG)
                    .setAction(R.string.undo_message) {
                        bookmarksViewModel.insertNews(news)
                        when (response?.status) {
                            Status.SUCCESS -> {
                                var list = response.data!!.articles
                                for (i in list.indices) {
                                    if (objectsEqual(news, list[i])) list[i].bookmark = 0
                                }
                            }
                            else -> {}
                        }
                        adapter.insertAt(position, news)
                    }.show()
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun update() {

    }
}