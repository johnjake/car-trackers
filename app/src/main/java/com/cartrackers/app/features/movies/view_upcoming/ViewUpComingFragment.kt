package com.cartrackers.app.features.movies.view_upcoming

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.map
import androidx.recyclerview.widget.LinearLayoutManager
import com.cartrackers.app.data.mapper.MapperMovie
import com.cartrackers.app.databinding.FragmentViewUpcomingBinding
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import org.koin.android.ext.android.inject

class ViewUpComingFragment: Fragment() {
    private var binding: FragmentViewUpcomingBinding? = null
    private val bind get() = binding
    private val viewModel: ComingAllViewModel by inject()
    private val comingAdapter: ComingAllAdapter by lazy { ComingAllAdapter() }
    private var allJob: Job? = null
    private val mapper = MapperMovie.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentViewUpcomingBinding.inflate(inflater, container, false)
        return bind?.root
    }

    @ExperimentalPagingApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializedAdapter(view)
        observeMovieData()
    }

    @ExperimentalPagingApi
    private fun observeMovieData() {
        allJob = viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.getTopMovies().collect {
                viewModel.getTopMovies().distinctUntilChanged().collectLatest { data ->
                    val dbData = data.map { discover -> mapper.upFromRoom(discover) }
                    comingAdapter.submitData(dbData)
                }
            }
        }
    }

    private fun initializedAdapter(view: View) {
        val resultLayout = LinearLayoutManager(view.context).apply {
            orientation = LinearLayoutManager.VERTICAL
        }
        binding?.apply {
            listUpcomingView.apply {
                layoutManager = resultLayout
                adapter = comingAdapter
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        allJob?.cancel()
    }
}
