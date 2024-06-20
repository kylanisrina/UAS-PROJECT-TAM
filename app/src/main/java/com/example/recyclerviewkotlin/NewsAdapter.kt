package com.example.recyclerviewkotlin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

data class News(val title: String, val description: String, val imageResId: Int)

class NewsAdapter(private val newsList: List<News>) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    inner class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.newsImageView)
        val titleTextView: TextView = itemView.findViewById(R.id.newsTitleTextView)
        val descriptionTextView: TextView = itemView.findViewById(R.id.newsDescriptionTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_news_adapter, parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val currentItem = newsList[position]
        holder.imageView.setImageResource(currentItem.imageResId)
        holder.titleTextView.text = currentItem.title
        holder.descriptionTextView.text = currentItem.description
    }

    override fun getItemCount(): Int {
        return newsList.size
    }
}
