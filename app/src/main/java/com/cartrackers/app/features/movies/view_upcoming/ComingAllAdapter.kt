package com.cartrackers.app.features.movies.view_upcoming

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.cartrackers.app.BuildConfig
import com.cartrackers.app.data.vo.movies.Discover
import com.cartrackers.app.databinding.ItemViewUpcomingBinding

class ComingAllAdapter : PagingDataAdapter<Discover, ComingAllViewHolder>(diffCallBack) {

    override fun onBindViewHolder(holder: ComingAllViewHolder, position: Int) {
        val currentItem = getItem(position)
        currentItem?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComingAllViewHolder {
        val binding = ItemViewUpcomingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ComingAllViewHolder(binding)
    }

    companion object {
        private val diffCallBack = object : DiffUtil.ItemCallback<Discover>() {
            override fun areItemsTheSame(oldItem: Discover, newItem: Discover) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Discover, newItem: Discover) =
                oldItem == newItem
        }
    }
}

class ComingAllViewHolder(private val binding: ItemViewUpcomingBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(movie: Discover) {
        val baseUrl = BuildConfig.BASE_URL_ORIGINAL
        binding.apply {
            avatarMovie.load(baseUrl+movie.poster_path)
            movieName.text = movie.original_title
            releaseDate.text = movie.release_date
            movieRating.rating = movie.vote_average.toFloat()/2
            rateAmount.text = movie.vote_average.toString()
        }
    }
}
