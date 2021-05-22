package com.cartrackers.app.features.movies.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.paging.map
import androidx.recyclerview.widget.LinearLayoutManager
import com.cartrackers.app.R
import com.cartrackers.app.data.mapper.MapperMovie
import com.cartrackers.app.databinding.FragmentSearchMainBinding
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class SearchDiscoverFragment : Fragment() {
    private var binding: FragmentSearchMainBinding? = null
    private val bind get() = binding
    private val searchAdapter: SearchDiscoverAdapter by lazy { SearchDiscoverAdapter() }
    private val viewModel: SearchViewModel by inject()
    private var searchJob: Job? = null
    private val mapper = MapperMovie.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchMainBinding.inflate(inflater, container, false)
        return bind?.root
    }

    @ExperimentalPagingApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializedAdapter(view)
        val query = savedInstanceState?.getString(last_query) ?: default_title
        searchQuery(query)
        initializedSearch(query)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(last_query, binding?.search?.text?.trim().toString())
    }

    @ExperimentalPagingApi
    private fun searchQuery(query: String) {
        searchJob?.cancel()
        searchJob = viewLifecycleOwner.lifecycleScope.launch {
            viewModel.searchMovie(query).collectLatest { data ->
                val dbData = data.map { discover -> mapper.mapFromRoom(discover) }
                searchAdapter.submitData(dbData)
            }
        }
    }

    private fun initializedAdapter(view: View) {
        val resultLayout = LinearLayoutManager(view.context).apply {
            orientation = LinearLayoutManager.VERTICAL
        }
        binding?.apply {
             listSearchResult.apply {
                layoutManager = resultLayout
                adapter = searchAdapter
            }
        }

        searchAdapter.addLoadStateListener { loadState ->
            val isListEmpty = loadState.refresh is LoadState.NotLoading && searchAdapter.itemCount == 0
            showEmptyList(isListEmpty)

            binding?.listSearchResult?.isVisible = loadState.mediator?.refresh is LoadState.NotLoading

            val isProgressBar = loadState.mediator?.refresh is LoadState.Loading
            showLoading(isProgressBar)

            val isRetryButton = loadState.mediator?.refresh is LoadState.Error

            val errorState = loadState.source.append as? LoadState.Error
                ?: loadState.source.prepend as? LoadState.Error
                ?: loadState.append as? LoadState.Error
                ?: loadState.prepend as? LoadState.Error
            errorState?.let {
                Toast.makeText(
                    context,
                    "\uD83D\uDE28 Wooops ${it.error}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    @ExperimentalPagingApi
    private fun initializedSearch(query: String) {
       binding?.search?.addTextChangedListener(object: TextWatcher{
           override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

           }

           override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

           }

           override fun afterTextChanged(s: Editable?) {
               if(s?.isNotEmpty() == true) {
                   searchQuery(s.toString())
               }
           }
       })
    }

    private fun showEmptyList(show: Boolean) {
        if (show) {
            binding?.emptyList?.visibility = View.VISIBLE
            binding?.listSearchResult?.visibility = View.GONE
        } else {
            binding?.emptyList?.visibility = View.GONE
            binding?.listSearchResult?.visibility = View.VISIBLE
        }
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

    companion object {
        private const val last_query: String = "last_search_query"
        private const val default_title = "Star Wars"
    }
}
