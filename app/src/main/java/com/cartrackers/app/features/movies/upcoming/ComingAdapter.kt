package com.cartrackers.app.features.movies.upcoming

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.cartrackers.app.BuildConfig
import com.cartrackers.app.data.vo.movies.Discover
import com.cartrackers.app.databinding.ItemUpcomingMoviesBinding

class ComingAdapter : PagingDataAdapter<Discover, ComingViewHolder>(diffCallBack) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComingViewHolder {
        val binding = ItemUpcomingMoviesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ComingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ComingViewHolder, position: Int) {
        val currentItem = getItem(position)
        currentItem?.let { holder.bind(it) }
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

class ComingViewHolder(private val binding: ItemUpcomingMoviesBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(movie: Discover) {
        val baseUrl = BuildConfig.BASE_URL_ORIGINAL
        binding.apply {
            textViewTopRated.text = movie.original_title
            // txtDate.text = movie.release_date
            imgTopRated.load(baseUrl+movie.poster_path)
        }
    }
}
