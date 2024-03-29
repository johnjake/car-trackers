package com.cartrackers.app.features.inbox

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cartrackers.app.data.vo.User
import com.cartrackers.app.databinding.ItemEmailInboxBinding
import com.cartrackers.app.extension.toAvatar
import com.cartrackers.app.features.common.ProfileOnClickListener

class InboxAdapter(private val listener: ProfileOnClickListener): ListAdapter<User, InboxAdapter.InboxViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InboxViewHolder {
        val binding = ItemEmailInboxBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return InboxViewHolder(binding)
    }

    override fun onBindViewHolder(holder: InboxViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem, listener)
    }


    inner class InboxViewHolder(private val binding: ItemEmailInboxBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User, listener: ProfileOnClickListener) {
            binding.apply {
                if(user.id!=0) {
                    user.id?.let { avatarInbox.toAvatar(it, binding.root.context) }
                }
                completeName.text = user.name
                emailAddressInbox.text = user.email
                avatarInbox.setOnClickListener {
                    user.id?.let { view -> listener.onClickListener(view) }
                }
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