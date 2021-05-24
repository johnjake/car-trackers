package com.cartrackers.app.features.movies.slider

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import coil.load
import com.cartrackers.app.BuildConfig
import com.cartrackers.app.R
import com.cartrackers.app.data.vo.movies.Discover
import com.cartrackers.app.databinding.ItemSliderMoviesBinding
import timber.log.Timber

class SliderAdapter(private val context: Context): PagerAdapter() {

    private var movieList: List<Discover> = emptyList()
    private lateinit var binding: ItemSliderMoviesBinding

    var dataSource: List<Discover>
        get() = movieList
        set(value) {
            movieList = value
            notifyDataSetChanged()
        }

    @SuppressLint("SetTextI18n")
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val baseUrlBackground = BuildConfig.BASE_URL_POSTER
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val binding: View = inflater.inflate(R.layout.item_slider_movies, null)
        binding.findViewById<TextView>(R.id.movieTitle).text = movieList[position].original_title
        binding.findViewById<TextView>(R.id.releaseData).text = movieList[position].release_date
        binding.findViewById<ImageView>(R.id.imgTopRated).load(baseUrlBackground + movieList[position].backdrop_path)
            val rating = movieList[position].vote_average
            val rate = rating/2
        binding.findViewById<RatingBar>(R.id.sliderRatingBar).rating = rate.toFloat()
        binding.findViewById<TextView>(R.id.slideRating).text = "($rating)"
        Timber.d("Slider #### ${movieList[position].original_title}")
        binding.findViewById<TextView>(R.id.movieSynapses).text = movieList[position].overview
        container.addView(binding)
        return binding
    }

    override fun getCount(): Int {
        return movieList.size
    }

    override fun isViewFromObject(view: View, objt: Any): Boolean {
        return view === objt
    }

    override fun destroyItem(container: ViewGroup, position: Int, objt: Any) {
        (container as ViewPager).removeView(objt as View)
    }
}
