package com.cartrackers.app.features.intro

import android.os.Bundle
import android.util.Log
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.cartrackers.app.R
import com.cartrackers.app.data.vo.State
import com.cartrackers.app.data.vo.User
import com.cartrackers.app.databinding.ActivityIntroBinding
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import timber.log.Timber

class IntroActivity : AppCompatActivity() {
    private lateinit var binding: ActivityIntroBinding
    private var stateJob: Job? = null
    private val viewModel: ViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupLogoAnimate()
        viewModel.getUserFromDomain()
    }

    override fun onStart() {
        super.onStart()
        stateJob = lifecycleScope.launch {
             viewModel.listDomainState.take(2).collect { state ->
                 handleDomainListResult(state)
             }
        }
    }

    override fun onStop() {
        stateJob?.cancel()
        super.onStop()
    }

    private fun setupLogoAnimate() {
        val rotation = AnimationUtils.loadAnimation(this, R.anim.anim_rotation)
        binding.imgLogo.startAnimation(rotation)
    }

    private fun handleDomainListResult(state: State<List<User>>) {
        when(state) {
            is State.Data -> handleDomainSuccess(state.data)
            is State.Error -> handleDomainFailed(state.error)
            else -> Timber.e("An error occurred during query request!")
        }
    }

    private fun handleDomainSuccess(data: List<User>) {
        /** map it to insert user password **/
        data.map { user ->
            val userId = user.id
            user.password = "password$userId"
            Log.d("handleDomainSuccess", "$user")
            viewModel.insertUserToDB(user)
        }
    }

    private fun handleDomainFailed(error: Throwable) {
        Timber.e("error on ${error.message}")
    }
}