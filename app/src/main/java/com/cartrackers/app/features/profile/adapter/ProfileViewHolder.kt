package com.cartrackers.app.features.profile.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cartrackers.app.R
import com.cartrackers.app.data.vo.User
import com.cartrackers.app.extension.toAvatar
import de.hdodenhof.circleimageview.CircleImageView

class ProfileViewHolder(private val view: View): RecyclerView.ViewHolder(view) {
    private val email: TextView = view.findViewById(R.id.emailAddress)
    private val avatar: CircleImageView = view.findViewById(R.id.avatar)
    val userName: TextView = view.findViewById(R.id.friendUserName)
    val userId: TextView = view.findViewById(R.id.friendUserId)
    fun bind(user: User) {
        email.text = user.email
        userName.text = user.username
        userId.text = user.id.toString()
        user.id?.let { avatar.toAvatar(it, view.context) }
    }

    companion object {
        fun create(parent: ViewGroup): ProfileViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_friends_list, parent, false)
            return ProfileViewHolder(view)
        }
    }
}