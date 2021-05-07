package com.cartrackers.app.features.movies.week

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.cartrackers.app.BuildConfig
import com.cartrackers.app.data.vo.movies.Discover
import com.cartrackers.app.databinding.ItemWeeklyMoviesBinding
import timber.log.Timber

class WeeklyAdapter : PagingDataAdapter<Discover, WeeklyViewHolder>(diffCallBack) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeeklyViewHolder {
        val binding = ItemWeeklyMoviesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WeeklyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WeeklyViewHolder, position: Int) {
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

class WeeklyViewHolder(private val binding: ItemWeeklyMoviesBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(movie: Discover) {
        Timber.d("movie $movie")
        val baseUrl = BuildConfig.BASE_URL_ORIGINAL
        binding.apply {
            txtMovieTitle.text = movie.original_title
            txtDate.text = movie.release_date
            imgView.load(baseUrl+movie.poster_path)
        }
    }
}
