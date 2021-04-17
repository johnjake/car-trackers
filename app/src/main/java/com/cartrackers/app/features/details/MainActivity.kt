package com.cartrackers.app.features.details

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.cartrackers.app.data.vo.State
import com.cartrackers.app.data.vo.User
import com.cartrackers.app.databinding.ActivityMainBinding
import com.cartrackers.app.utils.toStringType
import com.cartrackers.app.features.home.HomeViewModel as HomeModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var stateJob: Job? = null
    private val viewModel: HomeModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.getUserFromDomain()
    }

    override fun onStart() {
        super.onStart()
        /** collect */
        stateJob = lifecycleScope.launch {
            viewModel.listDomainState.take(2).collect { state ->
                handleDomainListResult(state)
            }
        }
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
            Log.d("USER: ", "$user")
            viewModel.insertUserToDB(user)
        }
    }

    private fun handleDomainFailed(error: Throwable) {
        Timber.e("error on ${error.message}")
    }

    override fun onStop() {
        stateJob?.cancel()
        super.onStop()
    }
    /** TODO getlist() or verify room successfully inserted the data **/
}