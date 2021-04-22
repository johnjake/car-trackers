package com.cartrackers.app.features.intro

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.cartrackers.app.R
import com.cartrackers.app.data.vo.State
import com.cartrackers.app.data.vo.User
import com.cartrackers.app.databinding.ActivityIntroBinding
import com.cartrackers.app.di.providesSharedPrefGetCount
import com.cartrackers.app.di.providesSharedPrefGetStorage
import com.cartrackers.app.di.providesSharedPrefStored
import com.cartrackers.app.di.providesSharedUserCount
import com.cartrackers.app.features.country.CountryActivity
import com.cartrackers.app.utils.shared_counter
import com.cartrackers.app.utils.shared_room
import com.cartrackers.app.utils.shared_user_no
import com.cartrackers.app.utils.toast
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
    private var counter: Int? = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupLogoAnimate()
        val storageRoom = providesSharedPrefGetStorage(this, shared_room)
        counter = providesSharedPrefGetCount(binding.root.context, shared_counter)
        if(storageRoom == false) {
            viewModel.getUserFromDomain()
            viewModel.insertCountryToDB()
        }
        if(counter ?:0 > 0) {
            binding.userSplashNextButton.isEnabled = true
        }
    }

    override fun onStart() {
        super.onStart()
        stateJob = lifecycleScope.launch {
             viewModel.listDomainState.take(2).collect { state ->
                 handleDomainListResult(state)
             }
        }

        binding.userSplashNextButton.setOnClickListener {
            launchActivity()
        }

    }

    override fun onStop() {
        stateJob?.cancel()
        super.onStop()
    }

    override fun onResume() {
        super.onResume()
        overridePendingTransition(R.anim.slide_in_top, R.anim.slide_out_top)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        exitApp()
    }

    private fun exitApp() {
        finishAndRemoveTask()
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
        if(data.isNotEmpty()) {
            data.map { user ->
                val userId = user.id
                user.email = user.email?.toLowerCase()
                user.password = "password$userId"
                viewModel.insertUserToDB(user)
            }
            this.toast("${data.size} no of data persist to room")
            providesSharedUserCount(this, shared_user_no, data.count())
            providesSharedPrefStored(this, shared_room, true)
            binding.userSplashNextButton.isEnabled = true
            providesSharedUserCount(this, shared_counter, 1)
        } else {
            providesSharedPrefStored(this, shared_room, false)
            this.toast("0 no of data persist to room")
        }
    }

    private fun handleDomainFailed(error: Throwable) {
        Timber.e("error on ${error.message}")
    }

    private fun launchActivity() {
        startActivity(Intent(this, CountryActivity::class.java).apply {
            putExtra("INTERNET", "1")
        })
    }

    /**TODO: login layout **/

}