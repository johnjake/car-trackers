package com.cartrackers.app.features.profile.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cartrackers.app.R
import com.cartrackers.app.data.vo.User

class ProfileViewHolder(private val view: View): RecyclerView.ViewHolder(view) {
    private val email: TextView = view.findViewById(R.id.tagName)
    val userName: TextView = view.findViewById(R.id.friendUserName)
    val userId: TextView = view.findViewById(R.id.friendUserId)
    fun bind(user: User) {
        email.text = user.email
        userName.text = user.username
        userId.text = user.id.toString()
    }

    companion object {
        fun create(parent: ViewGroup): ProfileViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_feed_main, parent, false)
            return ProfileViewHolder(view)
        }
    }
}