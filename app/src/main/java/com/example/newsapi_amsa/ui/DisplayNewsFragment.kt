package com.example.newsapi_amsa.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.newsapi_amsa.R
import com.example.newsapi_amsa.model.Article
import com.example.newsapi_amsa.utils.Utils
import kotlinx.android.synthetic.main.single_api_news.*

class DisplayNewsFragment(val article: Article, val fromLocalDatabase: Boolean) : Fragment(), View.OnClickListener {
    private lateinit var dispatcher : OnBackPressedDispatcher
    private lateinit var callback: OnBackPressedCallback

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_display_news, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        dispatcher = requireActivity().onBackPressedDispatcher
        callback = dispatcher.addCallback(this) {
            callback.isEnabled = false
            // dispatcher.onBackPressed()
            fragmentManager!!.popBackStack()
        }

        var title = view?.findViewById(R.id.display_article_title) as TextView
        title.text = article.title

        var description = view?.findViewById(R.id.display_article_description) as TextView
        description.text = article.description

        var dataSource = view?.findViewById(R.id.display_article_source_date) as TextView
        dataSource.text = String.format("%s, %s", article.source.name, Utils.formatDate(article.publishedAt))

        var image: ImageView = view?.findViewById(R.id.displayImage_imageView) as ImageView

        Glide.with(image)
            .load(article.urlToImage)
            .onlyRetrieveFromCache(true)
            .error(R.drawable.ic_error_red_72dp)
            .into(image)

        val srcButton = view?.findViewById(R.id.src_button) as Button
        srcButton.text = article.source.name.toLowerCase()
        srcButton.setOnClickListener(this)

    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.src_button -> {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(article.url)))
            }
        }
    }
}
