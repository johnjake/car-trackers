package com.cartrackers.app.features.movies

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
import com.cartrackers.app.databinding.FragmentMoviesBinding
import com.cartrackers.app.features.movies.vertical.VerticalAdapter
import com.cartrackers.app.features.movies.vertical.VerticalViewModel
import com.cartrackers.app.widget.SpacingItemDecoration
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import timber.log.Timber

class MovieFragment : Fragment() {
    private var binding: FragmentMoviesBinding? = null
    private val bind get() = binding
    private var stateJob: Job? = null
    private val viewModel: VerticalViewModel by inject()
    private val verticalAdapter: VerticalAdapter by lazy { VerticalAdapter() }
    private val mapper = MapperMovie.getInstance()
    private lateinit var resultLayout: LinearLayoutManager
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMoviesBinding.inflate(inflater, container, false)
        return bind?.root
    }

    @ExperimentalPagingApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()

        stateJob = viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getTopMovies().distinctUntilChanged().collectLatest { data ->
                val dbData = data.map { discover -> mapper.mapFromRoom(discover) }
                dbData.map {
                    Timber.d("movies: $it")
                }
                verticalAdapter.submitData(dbData)
            }
        }
    }

    private fun initAdapter() {
        val decorationStyle = SpacingItemDecoration(2, 75, true)
       binding.apply {
            binding?.listTopMovie?.apply {
                adapter = verticalAdapter
                addItemDecoration(decorationStyle)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        stateJob?.cancel()
    }
}
