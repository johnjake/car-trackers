package com.cartrackers.app.features.cars

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.cartrackers.app.data.vo.CarModel
import com.cartrackers.app.data.vo.State
import com.cartrackers.app.databinding.FragmentCarsBinding
import com.cartrackers.app.extension.showNavigation
import com.cartrackers.app.features.cars.adapter.CarsAdapter
import com.cartrackers.app.features.common.ProfileOnClickListener
import com.cartrackers.app.features.main.CarTrackActivity
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import timber.log.Timber

class CarsFragment: Fragment(), ProfileOnClickListener {
    private var binding: FragmentCarsBinding? = null
    private val bind get() = binding
    private val carAdapter: CarsAdapter by lazy { CarsAdapter(this) }
    private val viewModel: ViewModel by inject()
    private var stateJob: Job? = null
    private lateinit var resultLayout: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCarsBinding.inflate(inflater, container, false)
        return bind?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter(view)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        stateJob = lifecycleScope.launch {
            viewModel.carState.collect { state ->
                handleStateCars(state)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        bottomVisibility()
    }

    private fun handleStateCars(state: State<List<CarModel>>) {
        when(state) {
            is State.Data -> handlesSuccess(state.data)
            is State.Error -> handlesFailed(state.error)
            else -> Timber.e("An error occurred during query request!")
        }
    }

    private fun handlesFailed(error: Throwable) {
        Timber.e("Error: ${error.message}")
    }

    private fun handlesSuccess(data: List<CarModel>) {
        if(data.isNotEmpty()) {
            carAdapter.submitList(data)
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.getCarList()
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
        binding?.carList?.apply {
            layoutManager = resultLayout
            adapter = carAdapter
        }
    }

    private fun bottomVisibility() {
        if(CarTrackActivity.onBackPress) {
            CarTrackActivity.onBackPress = false
            activity.showNavigation()
        }
    }

    override fun onClickListener(userId: Int) {
        val args = CarsFragmentDirections.actionCarsToProfile(userId)
        CarTrackActivity.onBackPress = true
        view?.findNavController()?.navigate(args)
    }
}