package com.example.newsapi_amsa.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.newsapi_amsa.R
import com.example.newsapi_amsa.model.Article
import com.example.newsapi_amsa.utils.Utils
import kotlinx.android.synthetic.main.single_api_news.*

class DisplayNewsFragment(val article: Article) : Fragment(), View.OnClickListener {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_display_news, container, false)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return super.onContextItemSelected(item)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        var title = view?.findViewById(R.id.display_article_title) as TextView
        title.text = article.title

        var description = view?.findViewById(R.id.display_article_description) as TextView
        description.text = article.description

        var dataSource = view?.findViewById(R.id.display_article_source_date) as TextView
        dataSource.text = String.format("%s, %s", article.source.name, Utils.formatDate(article.publishedAt))

        var image: ImageView = view?.findViewById(R.id.displayImage_imageView) as ImageView

        if(article.bookmark == 1){
            button_add.setImageResource(R.drawable.ic_star_white_32dp)
        }
        else if(article.bookmark == 0){
            button_add.setImageResource(R.drawable.ic_star_border_white_32dp)
        }

        button_add.setOnClickListener {
            //ADDING
            if(article.bookmark == 0){
                article.bookmark = 1
                button_add.setImageResource(R.drawable.ic_star_white_32dp)
            }
            //DELETING
            else if(article.bookmark == 1) {
                article.bookmark = 0
                button_add.setImageResource(R.drawable.ic_star_border_white_32dp)
            }
        }

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
