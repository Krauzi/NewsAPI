package com.example.newsapi_amsa.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapi_amsa.R
import com.example.newsapi_amsa.model.Article
import kotlinx.android.synthetic.main.single_api_news.view.*

class MainPageAdapter(val onItemClick: (Article) -> Unit) : RecyclerView.Adapter<MainPageAdapter.ViewHolder>()  {
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
        holder.articleSourceText.text = String.format("%s, %s", article.source.name, article.publishedAt)
        holder.articleDescriptionText.text = article.description
    }

    fun setArticles(articles: List<Article>) {
        this.articleList = articles as MutableList<Article>
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val articleTitleText: TextView = view.article_title
        val articleSourceText: TextView = view.article_source_date
        val articleDescriptionText: TextView = view.article_description

        init {
            view.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    onItemClick(articleList[adapterPosition])
                }
            }
        }
    }

}