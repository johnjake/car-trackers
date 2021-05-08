package com.cartrackers.app.features.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.map
import com.cartrackers.app.R
import com.cartrackers.app.data.mapper.MapperMovie
import com.cartrackers.app.databinding.FragmentMoviesBinding
import com.cartrackers.app.features.movies.upcoming.ComingAdapter
import com.cartrackers.app.features.movies.upcoming.ComingViewModel
import com.cartrackers.app.features.movies.vertical.VerticalAdapter
import com.cartrackers.app.features.movies.vertical.VerticalViewModel
import com.cartrackers.app.features.movies.week.WeeklyAdapter
import com.cartrackers.app.features.movies.week.WeeklyViewModel
import com.cartrackers.app.widget.SpacingItemDecoration
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.koin.android.ext.android.inject

class MovieFragment : Fragment() {
    private var binding: FragmentMoviesBinding? = null
    private val bind get() = binding
    private var stateJob: Job? = null
    private var weekJob: Job? = null
    private var comingJob: Job? = null
    private var topJob: Job? = null
    private val viewModel: VerticalViewModel by inject()
    private val weeklyModel: WeeklyViewModel by inject()
    private val comingModel: ComingViewModel by inject()
    private val verticalAdapter: VerticalAdapter by lazy { VerticalAdapter() }
    private val weeklyAdapter: WeeklyAdapter by lazy { WeeklyAdapter() }
    private val comingAdapter: ComingAdapter by lazy { ComingAdapter() }
    private val mapper = MapperMovie.getInstance()
    private val isWeekly = MutableSharedFlow<Boolean>()
    private var observeOnce: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMoviesBinding.inflate(inflater, container, false)
        return bind?.root
    }

    @FlowPreview
    @ExperimentalPagingApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        observeWeekly()
        observerWeeklyMovies(view)
        observeWeeklyData()
        observeTopMovies()
        observeUpComingMovies()
        onClickMostPopular(view)
    }

    private fun onClickMostPopular(view: View) {
        binding?.mostPopular?.setOnClickListener {
            binding?.progressLoader?.visibility = View.VISIBLE
            runBlocking {
                binding?.thisWeek?.setTextColor(ContextCompat.getColor(view.context, R.color.textDefaultColor))
                binding?.mostPopular?.setTextColor(ContextCompat.getColor(view.context, R.color.pinkOne))
                isWeekly.emit(false)
            }
        }
    }

    @ExperimentalPagingApi
    private fun observeTopMovies() {
       topJob = viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getTopMovies().distinctUntilChanged().collectLatest { data ->
                val dbData = data.map { discover -> mapper.mapFromRoom(discover) }
                verticalAdapter.submitData(dbData)
                isWeekly.emit(false)
            }
        }
    }

    @ExperimentalPagingApi
    private fun observeUpComingMovies() {
       comingJob = viewLifecycleOwner.lifecycleScope.launch {
            comingModel.getTopMovies().distinctUntilChanged().collectLatest { data ->
                val dbData = data.map { discover -> mapper.upFromRoom(discover) }
                comingAdapter.submitData(dbData)
            }
        }
    }

    @ExperimentalPagingApi
    private fun observeWeeklyData() {
        viewLifecycleOwner.lifecycleScope.launch {
            weeklyModel.getWeeklyMovies().distinctUntilChanged().collect { data ->
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
                isWeekly.emit(true)
            }
        }
    }

    @FlowPreview
    private fun observeWeekly() {
      stateJob = viewLifecycleOwner.lifecycleScope.launch {
            isWeekly
                .debounce(1000)
                .collect { weekly ->
                      if(weekly) {
                         binding?.listTopMovie?.visibility = View.GONE
                         binding?.listThisWeek?.visibility = View.VISIBLE
                         binding?.progressLoader?.visibility = View.GONE
                     } else {
                         binding?.listTopMovie?.visibility = View.VISIBLE
                         binding?.listThisWeek?.visibility = View.GONE
                         binding?.progressLoader?.visibility = View.GONE
                     }
                }
        }
    }

    private fun initAdapter() {
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
            adapter = comingAdapter
            addItemDecoration(decorationStyle)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        comingJob?.cancel()
        weekJob?.cancel()
        topJob?.cancel()
        stateJob?.cancel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        comingJob?.cancel()
        weekJob?.cancel()
        topJob?.cancel()
        stateJob?.cancel()
    }
}
