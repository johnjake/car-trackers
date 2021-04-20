package com.cartrackers.app.features.track

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.cartrackers.app.R
import com.cartrackers.app.data.vo.State
import com.cartrackers.app.data.vo.User
import com.cartrackers.app.databinding.FragmentFeedBinding
import com.cartrackers.app.features.main.CarTrackActivity
import com.cartrackers.app.features.track.adapter.FeedAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import timber.log.Timber

class FeedFragment: Fragment(), FeedAdapter.ProfileOnClickListener {
    private var binding: FragmentFeedBinding? = null

    private val bind get() = binding

    private val viewModel: ViewModel by inject()

    private val userAdapter: FeedAdapter by lazy { FeedAdapter(this) }

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

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        stateJob =  lifecycleScope.launch {
            viewModel.listModelState.collect { state ->
                handleListFromRoom(state)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        bottomVisibility()
    }

    override fun onStart() {
        super.onStart()
        viewModel.getListFromRoom()
    }

    private fun initAdapter(view: View) {
        resultLayout = LinearLayoutManager(view.context).apply {
            orientation = LinearLayoutManager.VERTICAL
        }
        binding?.mapList?.apply {
            layoutManager = resultLayout
            adapter = userAdapter
        }
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
            Log.d("Data", "$data")
            userAdapter.dataSource = data
        }
    }

    private fun handleModelFailed(error: Throwable) {
        TODO("Not yet implemented")
    }

    private fun bottomVisibility() {
        if(CarTrackActivity.onBackPress) {
            CarTrackActivity.onBackPress = false
            val bottomNavigationView = activity?.findViewById<BottomNavigationView>(R.id.bottomNavigation)
            bottomNavigationView?.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
        stateJob?.cancel()
    }

    companion object {
        fun newInstance(filter: String): Fragment {
            val fragment = FeedFragment()
            val args = Bundle()
            args.putString(ARGS_FILTER, filter)
            fragment.arguments = args
            return fragment
        }
        private const val ARGS_FILTER = "ARGS_FILTER"
        private const val VISIBLE_THRESHOLD = 1
        private const val THRESHOLD_DELAY = 600L
    }

    override fun profileOnClick(userId: Int) {
        val args = FeedFragmentDirections.actionFeedToProfile(userId)
        CarTrackActivity.onBackPress = true
        view?.findNavController()?.navigate(args)
    }
}