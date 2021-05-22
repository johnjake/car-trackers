package com.cartrackers.app.features.movies.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.cartrackers.app.BuildConfig
import com.cartrackers.app.data.vo.movies.Discover
import com.cartrackers.app.databinding.ItemDiscoverSearchBinding

class SearchDiscoverAdapter : PagingDataAdapter<Discover, SearchDiscoverViewHolder>(diffCallBack) {

    override fun onBindViewHolder(holder: SearchDiscoverViewHolder, position: Int) {
        val currentItem = getItem(position)
        currentItem?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchDiscoverViewHolder {
        val binding = ItemDiscoverSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchDiscoverViewHolder(binding)
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

class SearchDiscoverViewHolder(private val binding: ItemDiscoverSearchBinding) : RecyclerView.ViewHolder(binding.root) {
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
