package com.yg.myrealtrip

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.newslist_item.view.*

class NewsListAdapter(val newsList: ArrayList<NewsListItem>) :
    RecyclerView.Adapter<NewsListAdapter.NewsListViewHolder>() {
    inner class NewsListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //TODO thumbnail
        fun bind(_newsList: NewsListItem) {
            Glide.with(itemView).load(_newsList.newsThumbnailLink)
                .into(itemView.newsItem_thumbnail)
            itemView.newsItem_title.text = _newsList.newsTitle
            itemView.newsItem_description.text = _newsList.newsDescription
            itemView.key0.text = _newsList.newsKey0
            itemView.key1.text = _newsList.newsKey1
            itemView.key2.text = _newsList.newsKey2
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsListViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.newslist_item, parent, false)

        return NewsListViewHolder(v)
    }

    override fun onBindViewHolder(holder: NewsListViewHolder, position: Int) {
        holder.bind(newsList[position])
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

}