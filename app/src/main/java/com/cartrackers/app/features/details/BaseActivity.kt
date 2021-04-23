package com.cartrackers.app.features.details

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.cartrackers.app.data.vo.State
import com.cartrackers.app.data.vo.User
import com.cartrackers.app.databinding.ActivityBaseBinding
import com.cartrackers.app.features.home.HomeViewModel as HomeModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import timber.log.Timber

class BaseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBaseBinding
    private var stateJob: Job? = null
    private val viewModel: HomeModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBaseBinding.inflate(layoutInflater)
        setContentView(binding.root)
       // viewModel.getUserFromDomain()
        viewModel.getListFromRoom()
    }

    override fun onStart() {
        super.onStart()
        /** collect */
        stateJob = lifecycleScope.launch {
            viewModel.listDomainState.take(2).collect { state ->
               handleDomainListResult(state)
            }
            viewModel.listModelState.take(2).collect { state ->
                handleListFromRoom(state)
            }
        }
    }

    private fun handleListFromRoom(state: State<List<User>>) {
        when(state) {
            is State.Data -> handleModelSuccess(state.data)
            is State.Error -> handleModelFailed(state.error)
            else -> Timber.e("An error occurred during query request!")
        }
    }

    private fun handleModelFailed(error: Throwable) {
        TODO("Not yet implemented")
    }

    private fun handleModelSuccess(data: List<User>) {
        binding.helloWorld.text = data[0].password
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