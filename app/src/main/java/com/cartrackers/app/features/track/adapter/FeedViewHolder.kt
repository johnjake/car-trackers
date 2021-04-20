package com.cartrackers.app.features.track.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.cartrackers.app.R
import com.cartrackers.app.data.vo.User
import com.cartrackers.app.extension.toAvatar
import de.hdodenhof.circleimageview.CircleImageView

class FeedViewHolder(private val view: View): RecyclerView.ViewHolder(view) {
    val name: TextView = view.findViewById(R.id.carModel)
    private val image: ImageView = view.findViewById(R.id.imageMap)
    private val userId: TextView = view.findViewById(R.id.userId)
    val content: TextView = view.findViewById(R.id.content)
    private val avatar: CircleImageView = view.findViewById(R.id.avatar)

    @SuppressLint("SetTextI18n")
    fun bind(user: User, itemListener: FeedAdapter.ProfileOnClickListener) {
        name.text = user.name
        image.setImageDrawable(ContextCompat.getDrawable(view.context, R.drawable.ic_map_item))
        val id = user.id.toString()
        userId.text = "ID: $id"
        val companyName = user.company.name
        val phrase = user.company.catchPhrase
        content.text = "$companyName is a $phrase"
        avatar.toAvatar(user.id, view.context)
        avatar.setOnClickListener {
            itemListener.profileOnClick(user.id)
        }
    }

    companion object {
        fun create(parent: ViewGroup): FeedViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_feed_main, parent, false)
            return FeedViewHolder(view)
        }
    }
}