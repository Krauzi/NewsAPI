package com.example.newsapi_amsa.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapi_amsa.R
import com.example.newsapi_amsa.model.Article
import com.example.newsapi_amsa.utils.Utils
import kotlinx.android.synthetic.main.single_api_news.view.*

class BookmarksAdapter(val onItemClick: (Article) -> Unit) : RecyclerView.Adapter<BookmarksAdapter.ViewHolder>()  {
    private var articleList: MutableList<Article> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.single_api_news, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return articleList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var article = articleList[position]

        holder.articleTitleText.text = article.title
        holder.articleDescriptionText.text = article.description
        holder.articleSourceText.text =
            String.format("%s, %s", article.source.name, Utils.formatDate(article.publishedAt))

        Glide.with(holder.articleImageView)
            .load(article.urlToImage)
            .onlyRetrieveFromCache(true)
            .error(R.drawable.ic_error_red_72dp)
            .into(holder.articleImageView)
    }

    fun setArticles(articles: List<Article>) {
        this.articleList = articles as MutableList<Article>
        notifyDataSetChanged()
    }

    fun getArticleAt(position: Int): Article {
        return articleList[position]
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val articleTitleText: TextView = view.article_title
        val articleDescriptionText: TextView = view.article_description
        val articleSourceText: TextView = view.article_source_date
        val articleImageView: ImageView = view.image_imageView
        val articleProgressBar = view.image_progressBar
        val articleButtonAdd: ImageButton = view.button_add

        init {
            articleButtonAdd.visibility = View.GONE
            articleProgressBar.visibility = View.GONE

            view.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    onItemClick(articleList[adapterPosition])
                }
            }
        }
    }
}