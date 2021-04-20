package com.cartrackers.app.features.profile.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cartrackers.app.data.vo.User

class ProfileAdapter: RecyclerView.Adapter<ProfileViewHolder>() {
    private var dataSources: List<User> = emptyList()

    var dataSource: List<User> get() = dataSources
        set(value) {
            dataSources = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileViewHolder {
        return ProfileViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ProfileViewHolder, position: Int) {
        holder.bind(dataSources[position])
    }

    override fun getItemCount(): Int = dataSource.size

}