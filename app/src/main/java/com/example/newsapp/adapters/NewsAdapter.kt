package com.example.newsapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.newsapp.R
import com.example.newsapp.models.Article
import com.example.newsapp.ui.extensions.format
import com.example.newsapp.ui.extensions.toLocalDateTime

class NewsAdapter: RecyclerView.Adapter<NewsAdapter.ArticleViewHolder>() {

    inner class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    lateinit var articleImage : ImageView
    lateinit var articleSource: TextView
    lateinit var articleTitle : TextView
    lateinit var articleDescription : TextView
    lateinit var articleDateTime : TextView


    private val differCallBack = object:DiffUtil.ItemCallback<Article>(){
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return  oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallBack)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener : ((Article) -> Unit)? = null

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
       var article = differ.currentList[position]

        articleImage = holder.itemView.findViewById(R.id.articleImage)
        articleSource = holder.itemView.findViewById(R.id.articleSource)
        articleTitle = holder.itemView.findViewById(R.id.articleTitle)
        articleDescription = holder.itemView.findViewById(R.id.articleDescription)
        articleDateTime = holder.itemView.findViewById(R.id.articleDateTime)


        holder.itemView.apply {
            Glide.with(this)
                .load(article.urlToImage)
                .transform(RoundedCorners(20))
                .into(articleImage)

            articleTitle.text = article.title
            articleSource.text = article.author
            articleDescription.text = article.description
            val formattedDate = article.publishedAt.toLocalDateTime()?.format("dd/MM/yyyy HH:mm") ?: "Geçersiz tarih"
            articleDateTime.text = formattedDate

            setOnClickListener{
                onItemClickListener?.let {
                    it(article)
                }
            }
        }
    }
    fun setOnItemClickListener(listener: (Article) -> Unit){
        onItemClickListener = listener
    }
}