package com.example.newsapi_amsa.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProviders
import com.example.newsapi_amsa.R
import com.example.newsapi_amsa.utils.*
import kotlinx.android.synthetic.main.fragment_search_inside.*

class SearchFragment : Fragment(), View.OnClickListener {

    private lateinit var searchViewModel: SearchViewModel

    private lateinit var searchButton: Button
    private lateinit var query: EditText

    private val BACK_STACK_ROOT_TAG = "root_fragment"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        query = view!!.findViewById(R.id.query_editText)
        searchButton = view!!.findViewById(R.id.search_button)

        val langAdapter = ArrayAdapter(context!!.applicationContext, R.layout.spinner_item, SpinnerLists.language)
        langAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
        lang_spinner.adapter = langAdapter

        val queryTypeAdapter = ArrayAdapter(context!!.applicationContext, R.layout.spinner_item, SpinnerLists.queryType)
        queryTypeAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
        query_spinner.adapter = queryTypeAdapter

        val olderThanAdapter = ArrayAdapter(context!!.applicationContext, R.layout.spinner_item, SpinnerLists.olderThan)
        olderThanAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
        from_spinner.adapter = olderThanAdapter

        val sortAdapter = ArrayAdapter(context!!.applicationContext, R.layout.spinner_item, SpinnerLists.sortBy)
        sortAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
        sort_spinner.adapter = sortAdapter

        searchButton.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.search_button -> {
                var language = lang_spinner.selectedItem as Language
                var query_type = query_spinner.selectedItem as QueryType
                var older_than = from_spinner.selectedItem as OlderThan
                var sort_by = sort_spinner.selectedItem as SortBy

                val data: HashMap<String, String> = HashMap()
                data[query_type.value] = query.text.toString()
                data["language"] = language.value
                data["from"] = older_than.value
                data["sortBy"] = sort_by.value

                searchViewModel = ViewModelProviders.of(activity!!).get(SearchViewModel::class.java)
                searchViewModel.refreshNews(data)


                childFragmentManager.beginTransaction()
                    .replace(R.id.search_fragment_container,
                        SearchResultsFragment(data))
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .addToBackStack(BACK_STACK_ROOT_TAG)
                    .commit()
            }
        }
    }
}