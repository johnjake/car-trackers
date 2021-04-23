package com.cartrackers.app.features.profile.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cartrackers.app.data.vo.User
import com.cartrackers.app.databinding.ItemFriendsListBinding
import com.cartrackers.app.extension.toAvatar

class ProfilesAdapter: ListAdapter<User, ProfilesAdapter.ProfilesViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfilesViewHolder {
        val binding = ItemFriendsListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProfilesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProfilesViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    inner class ProfilesViewHolder(private val binding: ItemFriendsListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            binding.apply {
                if(user.id!=0) {
                    user.id?.let { avatar.toAvatar(it, binding.root.context) }
                }
                friendUserName.text = user.username
                emailAddress.text = user.email
                friendUserId.text = user.id.toString()
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: User, newItem: User) =
            oldItem == newItem
    }
}