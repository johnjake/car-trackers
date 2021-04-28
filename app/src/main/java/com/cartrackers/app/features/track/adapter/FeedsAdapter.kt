package com.cartrackers.app.features.track.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cartrackers.app.R
import com.cartrackers.app.data.vo.User
import com.cartrackers.app.databinding.ItemFeedMainBinding
import com.cartrackers.app.extension.toAvatar
import com.cartrackers.app.extension.toJsonType

class FeedsAdapter(
    private val itemListener: (id: Int) -> Unit,
    private val onMapListener: (profile: User) -> Unit
): ListAdapter<User, FeedsAdapter.FeedsViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedsViewHolder {
        val binding = ItemFeedMainBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FeedsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FeedsViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    inner class FeedsViewHolder(private val binding: ItemFeedMainBinding): RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(user: User) {
            binding.apply {
                if(user.id!=0) {
                    user.id?.let { avatar.toAvatar(it, binding.root.context) }
                }
                content.text = "${user.company?.name} is a ${user.company?.catchPhrase}"
                carTrack.text = "UserId: ${user.id}"
                imageMap.setImageDrawable(ContextCompat.getDrawable(binding.root.context, R.drawable.ic_map_item))
                carModel.text = user.name

                avatar.setOnClickListener {
                    user.id?.let { item -> itemListener(item) }
                }

                imageMap.setOnClickListener {
                    onMapListener(user)
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