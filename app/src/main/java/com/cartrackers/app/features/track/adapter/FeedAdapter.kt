package com.cartrackers.app.features.track.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cartrackers.app.data.vo.User

class FeedAdapter(
    private val itemListener: ProfileOnClickListener,
    private val onMapListener: ProfileOnMapClickListener
    ): RecyclerView.Adapter<FeedViewHolder>() {

    private var dataSources: List<User> = emptyList()

    var dataSource: List<User> get() = dataSources
        set(value) {
            dataSources = value
            notifyDataSetChanged()
        }

    interface ProfileOnClickListener {
        fun profileOnClick(userId: Int)
    }

    interface ProfileOnMapClickListener {
        fun locationOnClick(profile: User)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        return FeedViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        holder.bind(dataSources[position], itemListener, onMapListener)
    }

    override fun getItemCount(): Int = dataSource.size

}


