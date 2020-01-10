package com.example.newsapi_amsa.adapters

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.example.newsapi_amsa.R
import com.example.newsapi_amsa.model.Article
import com.example.newsapi_amsa.utils.Utils
import kotlinx.android.synthetic.main.single_api_news.view.*


class HomePageAdapter(val onItemClick: (Article) -> Unit) : RecyclerView.Adapter<HomePageAdapter.ViewHolder>()  {
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
            .centerCrop()
            .error(R.drawable.ic_image_error)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(p0: GlideException?, model: Any?, p2: Target<Drawable>?, p3: Boolean): Boolean {
                    holder.articleProgressBar.visibility = View.GONE
                    return false
                }
                override fun onResourceReady(p0: Drawable?, model: Any?, p2: Target<Drawable>?, p3: DataSource?, p4: Boolean): Boolean {
                    holder.articleProgressBar.visibility = View.GONE
                    return false
                }
            }).transition(DrawableTransitionOptions.withCrossFade())
            .into(holder.articleImageView)
    }

    fun setArticles(articles: List<Article>) {
        this.articleList = articles as MutableList<Article>
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val articleTitleText: TextView = view.article_title
        val articleDescriptionText: TextView = view.article_description
        val articleSourceText: TextView = view.article_source_date
        val articleImageView: ImageView = view.image_imageView
        val articleProgressBar: ProgressBar = view.image_progressBar

        init {
            view.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    onItemClick(articleList[adapterPosition])
                }
            }
        }
    }

}