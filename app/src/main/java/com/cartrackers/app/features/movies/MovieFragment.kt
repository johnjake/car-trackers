package com.cartrackers.app.features.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.map
import androidx.recyclerview.widget.LinearLayoutManager
import com.cartrackers.app.R
import com.cartrackers.app.data.mapper.MapperMovie
import com.cartrackers.app.databinding.FragmentMoviesBinding
import com.cartrackers.app.features.movies.upcoming.ComingAdapter
import com.cartrackers.app.features.movies.upcoming.ComingViewModel
import com.cartrackers.app.features.movies.vertical.VerticalAdapter
import com.cartrackers.app.features.movies.vertical.VerticalViewModel
import com.cartrackers.app.features.movies.view_upcoming.ViewUpComingFragment
import com.cartrackers.app.features.movies.week.WeeklyAdapter
import com.cartrackers.app.features.movies.week.WeeklyViewModel
import com.cartrackers.app.widget.SpacingItemDecoration
import com.cartrackers.baseplate_persistence.model.DBUpComing
import com.cartrackers.baseplate_persistence.model.DBWeekly
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import org.koin.android.ext.android.inject
import timber.log.Timber
import java.util.concurrent.atomic.AtomicBoolean

class MovieFragment : Fragment() {
    private var binding: FragmentMoviesBinding? = null
    private var views: View? = null
    private val bind get() = binding
    private var stateJob: Job? = null
    private var weekJob: Job? = null
    private var comingJob: Job? = null
    private var topJob: Job? = null
    private var loadJob: Job? = null
    private val viewModel: VerticalViewModel by inject()
    private val weeklyModel: WeeklyViewModel by inject()
    private val comingModel: ComingViewModel by inject()
    private val verticalAdapter: VerticalAdapter by lazy { VerticalAdapter() }
    private val weeklyAdapter: WeeklyAdapter by lazy { WeeklyAdapter() }
    private val comingAdapter: ComingAdapter by lazy { ComingAdapter() }
    private val mapper = MapperMovie.getInstance()
    private val isWeekly = MutableSharedFlow<Boolean>()
    private var isLoading = MutableSharedFlow<Boolean>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMoviesBinding.inflate(inflater, container, false)
        views = bind?.root?.rootView
        return bind?.root
    }

    @FlowPreview
    @ExperimentalPagingApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter(view)
        observeTopMovies()
        observeWeeklyData()
        observeUpComingMovies()
        onClickMostPopular(view)
        setupViewUpComing(view)
        setupSearchCinema(view)
    }

    @FlowPreview
    @ExperimentalPagingApi
    override fun onResume() {
        super.onResume()
        observerLoading()
        observeWeekly()
        views?.let { observerWeeklyMovies(it) }
    }

    private fun setupSearchCinema(view: View) {
        binding?.searchCinema?.setOnClickListener {
            view.findNavController().navigate(R.id.action_to_search_discover)
        }
    }

    private fun setupViewUpComing(view: View) {
        binding?.seeAllBottom?.setOnClickListener {
            lifecycleScope.launch {
                isLoading.emit(false)
            }
            view.findNavController().navigate(R.id.action_movie_main_view_upcoming)
        }
    }

    override fun onPause() {
        super.onPause()
        loadJob?.cancel()
    }

    private fun onClickMostPopular(view: View) {
        binding?.mostPopular?.setOnClickListener {
            binding?.progressLoader?.visibility = View.VISIBLE
            viewLifecycleOwner.lifecycleScope.launch {
                binding?.thisWeek?.setTextColor(ContextCompat.getColor(view.context, R.color.textDefaultColor))
                binding?.mostPopular?.setTextColor(ContextCompat.getColor(view.context, R.color.pinkOne))
                isWeekly.emit(false)
                isLoading.emit(true)
            }
        }
    }

    @ExperimentalPagingApi
    private fun observeTopMovies() {
        topJob = lifecycleScope.launch {
            viewModel.getTopMovies()
                .distinctUntilChanged()
                .collectLatest { data ->
                val dbData = data.map { discover -> mapper.mapFromRoom(discover) }
                verticalAdapter.submitData(dbData)
                isWeekly.emit(false)
            }
        }
    }

    @ExperimentalPagingApi
    private fun observeUpComingMovies() {
        comingJob = lifecycleScope.launch() {
            comingModel.getTopMovies()
                .buffer()
                .distinctUntilChanged().collectLatest { data ->
                val dbData = data.map { discover -> mapper.upFromRoom(discover) }
                comingAdapter.submitData(dbData)
            }
        }
    }

    @ExperimentalPagingApi
    private fun observeWeeklyData() {
        weekJob = lifecycleScope.launch() {
            weeklyModel.getWeeklyMovies()
                .buffer()
                .distinctUntilChanged().collect { data ->
                val domainData = data.map { weekly -> mapper.weeklyFromRoom(weekly) }
                weeklyAdapter.submitData(domainData)
            }
        }
    }

    @ExperimentalPagingApi
    private fun observerWeeklyMovies(view: View) {
        binding?.thisWeek?.setOnClickListener {
            weekJob = viewLifecycleOwner.lifecycleScope.launch {
                binding?.progressLoader?.visibility = View.VISIBLE
                binding?.thisWeek?.setTextColor(ContextCompat.getColor(view.context, R.color.pinkOne))
                binding?.mostPopular?.setTextColor(ContextCompat.getColor(view.context, R.color.textDefaultColor))
                isLoading.emit(true)
                isWeekly.emit(true)
            }
        }
    }

    @FlowPreview
    private fun observeWeekly() {
        stateJob = viewLifecycleOwner.lifecycleScope.launch {
            isWeekly
                .debounce(500)
                .collect { weekly ->
                    if(weekly) {
                        binding?.listTopMovie?.visibility = View.GONE
                        binding?.listThisWeek?.visibility = View.VISIBLE
                        binding?.progressLoader?.visibility = View.GONE
                        isLoading.emit(false)
                    } else {
                        binding?.listTopMovie?.visibility = View.VISIBLE
                        binding?.listThisWeek?.visibility = View.GONE
                        binding?.progressLoader?.visibility = View.GONE
                        isLoading.emit(false)
                    }
                }
        }
    }

    @FlowPreview
    private fun observerLoading() {
        loadJob = lifecycleScope.launch {
            isLoading.collect {
                showLoading(it)
            }
        }
    }

    private fun initAdapter(view: View) {
        val resultLayout = LinearLayoutManager(view.context).apply {
            orientation = LinearLayoutManager.HORIZONTAL
        }

        val decorationStyle = SpacingItemDecoration(2, 75, true)
        binding?.listTopMovie?.apply {
            adapter = verticalAdapter
            addItemDecoration(decorationStyle)
        }
        binding?.listThisWeek?.apply {
            adapter = weeklyAdapter
            addItemDecoration(decorationStyle)
        }
        binding?.listUpComing?.apply {
            layoutManager = resultLayout
            adapter = comingAdapter
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        comingJob?.cancel()
        weekJob?.cancel()
        topJob?.cancel()
        stateJob?.cancel()
        loadJob?.cancel()
        Timber.d("ON DESTROY VIEW")
    }

    private fun showLoading(isLoading: Boolean) {
        when (isLoading) {
            true -> showAnimation(R.raw.loading)
        }
    }

    private fun showAnimation(animationResource: Int) {
        binding?.progressLoader?.visibility = View.VISIBLE
        binding?.progressLoader?.setAnimation(animationResource)
        binding?.progressLoader?.playAnimation()
    }
}
