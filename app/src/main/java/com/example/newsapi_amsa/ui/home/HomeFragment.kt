package com.example.newsapi_amsa.ui.home

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.core.view.get
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
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment() {
    private var data: HashMap<String, String> = HashMap()
    private lateinit var homeViewModel: HomeViewModel
    lateinit var thisPageAdapter: HomePageAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    private val BACK_STACK_ROOT_TAG = "root_fragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_home, container, false)

        data["country"] = "pl"

        recyclerView = root.findViewById(R.id.articles_recyclerView)
        swipeRefreshLayout = root.findViewById(R.id.swipe_to_refresh_home)

        recyclerLoad()

        homeViewModel = ViewModelProviders.of(activity!!).get(HomeViewModel::class.java)

        fetchNews()

        return root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_category, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.category_null -> if (data.containsKey("category")) data.remove("category")
            R.id.category_business -> data["category"] = "business"
            R.id.category_sports -> data["category"] = "sports"
            R.id.category_technology -> data["category"] = "technology"
            R.id.category_science -> data["category"] = "science"
            R.id.category_entertainment -> data["category"] = "entertainment"
            R.id.category_health -> data["category"] = "health"
        }
        homeViewModel.refreshNews(data, true)
        return true
    }

    private fun recyclerLoad() {
        swipeRefreshLayout.setOnRefreshListener {
            homeViewModel.refreshNews(HashMap(), false)
        }

        thisPageAdapter = HomePageAdapter ({
            childFragmentManager.beginTransaction()
                .replace(R.id.home_fragment_container, DisplayNewsFragment(it, false))
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(BACK_STACK_ROOT_TAG)
                .commit()

        }, {
            when(it.bookmark) {
                0 -> {
                    homeViewModel.insertNews(it)
                    Toast.makeText(context, R.string.toast_added, Toast.LENGTH_LONG).show()
                }
                1 -> {
                    homeViewModel.deleteNews(it)
                    Toast.makeText(context, R.string.toast_removed, Toast.LENGTH_LONG).show()
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
        homeViewModel.getNews().observe(viewLifecycleOwner, Observer<Resource<News>> {
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