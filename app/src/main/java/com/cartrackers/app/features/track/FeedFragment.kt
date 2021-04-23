package com.cartrackers.app.features.track

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.cartrackers.app.R
import com.cartrackers.app.data.vo.State
import com.cartrackers.app.data.vo.User
import com.cartrackers.app.databinding.FragmentFeedBinding
import com.cartrackers.app.extension.showNavigation
import com.cartrackers.app.extension.toJsonType
import com.cartrackers.app.features.main.CarTrackActivity
import com.cartrackers.app.features.track.adapter.FeedsAdapter
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import timber.log.Timber

class FeedFragment: Fragment(), FeedsAdapter.ProfileOnClickListener, FeedsAdapter.ProfileOnMapClickListener {
    private var binding: FragmentFeedBinding? = null

    private val bind get() = binding

    private val viewModel: ViewModel by inject()

    private val userAdapter: FeedsAdapter by lazy { FeedsAdapter(this, this) }

    private var stateJob: Job? = null

    private lateinit var resultLayout: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFeedBinding.inflate(inflater, container, false)
        return bind?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter(view)

        stateJob =  viewLifecycleOwner.lifecycleScope.launch {
            viewModel.listModelState.collect { state ->
                handleListFromRoom(state)
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding?.searchButton?.setOnClickListener {
            it.findNavController().navigate(R.id.action_search_item)
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.getListFromRoom()
    }

    override fun onResume() {
        super.onResume()
        bottomVisibility()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
        stateJob?.cancel()
    }

    private fun initAdapter(view: View) {
        resultLayout = LinearLayoutManager(view.context).apply {
            orientation = LinearLayoutManager.VERTICAL
        }
        binding?.mapList?.apply {
            layoutManager = resultLayout
            adapter = userAdapter
        }
        userAdapter.hasStableIds()
    }

    private fun handleListFromRoom(state: State<List<User>>) {
        when(state) {
            is State.Data -> handleModelSuccess(state.data)
            is State.Error -> handleModelFailed(state.error)
            else -> Timber.e("An error occurred during query request!")
        }
    }

    private fun handleModelSuccess(data: List<User>) {
        if(data.isNotEmpty()) {
            Timber.d( "$data")
            userAdapter.submitList(data)
        }
    }

    private fun handleModelFailed(error: Throwable) {
       Timber.e("Error: ${error.message}")
    }

    private fun bottomVisibility() {
        if(CarTrackActivity.onBackPress) {
            CarTrackActivity.onBackPress = false
            activity.showNavigation()
        }
    }

    override fun profileOnClick(userId: Int) {
        val args = FeedFragmentDirections.actionFeedToProfile(userId)
        CarTrackActivity.onBackPress = true
        view?.findNavController()?.navigate(args)
    }

    override fun locationOnClick(user: User) {
        val profileUser = user.toJsonType().toString()
        val args = FeedFragmentDirections.actionToLocation(profileUser)
        CarTrackActivity.onBackPress = true
        view?.findNavController()?.navigate(args)
    }
}