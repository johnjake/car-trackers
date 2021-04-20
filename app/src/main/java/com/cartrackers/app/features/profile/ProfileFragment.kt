package com.cartrackers.app.features.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.cartrackers.app.R
import com.cartrackers.app.data.vo.State
import com.cartrackers.app.data.vo.User
import com.cartrackers.app.databinding.FragmentProfileBinding
import com.cartrackers.app.extension.toAvatar
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import timber.log.Timber

class ProfileFragment: Fragment() {
    private var binding: FragmentProfileBinding? = null

    private val bind get() = binding

    private val viewModel: ViewModel by inject()

    private var stateJob: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return bind?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hideNavigation()

        binding?.backButton?.setOnClickListener {
            val onBackPressedCallback = object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    Timber.d("Leaving")
                    view.findNavController().navigate(R.id.action_back_to_main)
                }
            }
            requireActivity().onBackPressedDispatcher.addCallback(
                viewLifecycleOwner, onBackPressedCallback
            )
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        stateJob = lifecycleScope.launch {
            viewModel.userProfileState.collect {  state ->
                handleStateUser(state)
            }
        }
    }

    private fun handleStateUser(state: State<User>) {
        when(state) {
            is State.Data -> handleSuccessState(state.data)
            is State.Error -> handleFailedState(state.error)
            else -> Timber.e("An error occurred during query request!")
        }
    }

    private fun handleFailedState(error: Throwable) {
        TODO("Not yet implemented")
    }

    private fun handleSuccessState(data: User) {
        binding?.header?.UserName?.text = data.username
        binding?.header?.fullname?.text = data.name
        binding?.header?.webSite?.text = data.website
        binding?.header?.companyName?.text = data.company.name
        view?.context?.let { binding?.header?.avatar?.toAvatar(data.id, it) }
    }

    private fun hideNavigation() {
        val bottomNavigationView = activity?.findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNavigationView?.visibility = View.GONE
    }

    override fun onStart() {
        super.onStart()
        arguments?.let {
            val arg = ProfileFragmentArgs.fromBundle(it)
            viewModel.getUserProfile(arg.userId)
        }
    }
}