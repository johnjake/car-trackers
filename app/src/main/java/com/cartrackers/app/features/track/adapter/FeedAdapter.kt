package com.cartrackers.app.features.track.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cartrackers.app.data.vo.User

class FeedAdapter: RecyclerView.Adapter<FeedViewHolder>() {

    private var dataSources: List<User> = emptyList()

    var dataSource: List<User> get() = dataSources
        set(value) {
            dataSources = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        return FeedViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        holder.bind(dataSources[position])
    }

    override fun getItemCount(): Int = dataSource.size
}


