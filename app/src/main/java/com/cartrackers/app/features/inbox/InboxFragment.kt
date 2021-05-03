package com.cartrackers.app.features.inbox

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.cartrackers.app.data.vo.State
import com.cartrackers.app.data.vo.User
import com.cartrackers.app.databinding.FragmentManageBinding
import com.cartrackers.app.extension.showNavigation
import com.cartrackers.app.features.common.ProfileOnClickListener
import com.cartrackers.app.features.main.CarTrackActivity
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import timber.log.Timber

class InboxFragment: Fragment(), ProfileOnClickListener {
    private var binding: FragmentManageBinding? = null

    private val bind get() = binding

    private val viewModel: ViewModel by inject()

    private var stateJob: Job? = null

    private lateinit var resultLayout: LinearLayoutManager

    private val inboxAdapter: InboxAdapter by lazy { InboxAdapter(this) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentManageBinding.inflate(inflater, container, false)
        return bind?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        stateJob = viewLifecycleOwner.lifecycleScope.launch {
            viewModel.inboxState.collect {  state ->
                handleSuccessState(state)
            }
        }
        initAdapter(view)
    }

    override fun onStart() {
        super.onStart()
        viewModel.getInboxList(1)
    }

    override fun onResume() {
        super.onResume()
        bottomVisibility()
    }

    override fun onDestroy() {
        super.onDestroy()
        stateJob?.cancel()
    }

    private fun handleSuccessState(state: State<List<User>>) {
        when(state) {
            is State.Data -> handlesSuccess(state.data)
            is State.Error -> handleFailed(state.error)
            else -> Timber.e("An error occurred during query request!")
        }
    }

    private fun handleFailed(error: Throwable) {
        Timber.e("Error: ${error.message}")
    }

    private fun handlesSuccess(data: List<User>) {
        inboxAdapter.submitList(data)
    }

    private fun initAdapter(view: View) {
        resultLayout = LinearLayoutManager(view.context).apply {
            orientation = LinearLayoutManager.VERTICAL
        }
        binding.apply {
            binding?.inboxList?.apply {
                layoutManager = resultLayout
                adapter = inboxAdapter
                setHasFixedSize(true)
            }
        }
    }

    private fun bottomVisibility() {
        if(CarTrackActivity.onBackPress.value) {
            CarTrackActivity.onBackPress.value = false
            activity.showNavigation()
        }
    }

    override fun onClickListener(userId: Int) {
        val args = InboxFragmentDirections.actionInboxToProfile(userId)
        CarTrackActivity.onBackPress.value = true
        view?.findNavController()?.navigate(args)
    }
}