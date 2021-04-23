package com.cartrackers.app.features.profile

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Typeface
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
import com.cartrackers.app.databinding.FragmentProfileBinding
import com.cartrackers.app.extension.hideNavigation
import com.cartrackers.app.extension.toAvatar
import com.cartrackers.app.features.country.CountryActivity
import com.cartrackers.app.utils.alert_dialog.ListenerCallBack
import com.cartrackers.app.utils.alert_dialog.TrackerAlertDialog
import com.cartrackers.app.extension.toJsonType
import com.cartrackers.app.features.profile.adapter.ProfilesAdapter
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

    private lateinit var resultLayout: LinearLayoutManager

    private var userId: Int = 0

    private val userAdapter: ProfilesAdapter by lazy { ProfilesAdapter() }

    private lateinit var formattedProfile: String

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
        activity.hideNavigation()
        initAdapter(view)
        binding?.backButton?.setOnClickListener {
            view.findNavController().popBackStack()
        }

        binding?.editDetails?.setOnClickListener {
            val args = ProfileFragmentDirections.actionEditProfile(userId, formattedProfile)
            it.findNavController().navigate(args)
        }

        binding?.buttonLogout?.setOnClickListener {
            val alertDialog = TrackerAlertDialog()
            alertDialog.alertInitialize(
                it.context,
                "Car Track",
                "Hi would you like to sign-out?",
                Typeface.SANS_SERIF,
                Typeface.DEFAULT_BOLD,
                isCancelable = true,
                isNegativeBtnHide = false)
            alertDialog.setPositive("YES", object : ListenerCallBack {
                override fun onClick(dialog: TrackerAlertDialog) {
                    dialog.dismiss()
                    launchActivity()
                }
            })
            alertDialog.setNegative("NO", object : ListenerCallBack {
                override fun onClick(dialog: TrackerAlertDialog) {
                    dialog.dismiss()
                }
            })
            alertDialog.show()
        }
    }

    private fun launchActivity() {
        activity?.finish()
        val intent = Intent(activity, CountryActivity::class.java)
        startActivity(intent)
    }

    override fun onStart() {
        super.onStart()
        arguments?.let {
            val arg = ProfileFragmentArgs.fromBundle(it)
            viewModel.getUserProfile(arg.userId)
            viewModel.getListFromRoom(arg.userId)
            userId = arg.userId
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        stateJob?.cancel()
    }

    private fun initAdapter(view: View) {
        resultLayout = LinearLayoutManager(view.context).apply {
            orientation = LinearLayoutManager.HORIZONTAL
        }
        binding?.listFriends?.apply {
            layoutManager = resultLayout
            adapter = userAdapter
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        stateJob = lifecycleScope.launch {
            viewModel.userProfileState.collect {  state ->
                handleStateUser(state)
            }
        }
        stateJob =  lifecycleScope.launch {
            viewModel.listModelState.collect { state ->
                handleListFromRoom(state)
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
        Timber.e("An error: ${error.message}")
    }

    private fun handleSuccessState(data: User) {
        binding?.header?.UserName?.text = data.username
        binding?.header?.fullname?.text = data.name
        binding?.header?.webSite?.text = data.website
        binding?.header?.companyName?.text = data.company?.name
        view?.context?.let { data.id?.let { it1 -> binding?.header?.avatar?.toAvatar(it1, it) } }
        formattedProfile = data.toJsonType().toString()
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
            Timber.d("$data")
            userAdapter.submitList(data)
        }
    }

    @SuppressLint("TimberExceptionLogging")
    private fun handleModelFailed(error: Throwable) {
        Timber.d("${error.message}")
    }
}