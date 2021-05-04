package com.cartrackers.app.features.splash

import android.os.Bundle
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.cartrackers.app.comms.CarDialog
import com.cartrackers.app.databinding.ActivitySplashBinding
import com.cartrackers.app.di.providesSharedPrefGetCount
import com.cartrackers.app.di.providesSharedPrefStored
import com.cartrackers.app.di.providesSharedUserCount
import com.cartrackers.app.features.intro.IntroActivity
import com.cartrackers.app.extension.isOnline
import com.cartrackers.app.extension.net_connectivity
import com.cartrackers.app.extension.shared_counter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {
    private val scope = CoroutineScope(Dispatchers.IO)

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val counter = providesSharedPrefGetCount(binding.root.context, shared_counter)
        if (counter == 0) {
            providesSharedUserCount(this, shared_counter, 0)
        }
    }

    override fun onStart() {
        super.onStart()
        when (isOnline(this)) {
            true -> {
                scope.launch {
                    delay(5000)
                    launchActivity()
                    saveInternetStatePref(false)
                }
            }
            false -> verifyConnection()
        }
    }

    private fun launchActivity() {
        startActivity(Intent(this, IntroActivity::class.java).apply {
            putExtra("INTERNET", "1")
        })
    }

    private fun verifyConnection() {
        val value = CarDialog.build(this, "No Internet", "Would you like to switch to offline?")
        when {
            value -> {
                saveInternetStatePref(true)
                launchActivity()
            }
            else -> exitApp()
        }
    }

    private fun exitApp() {
        finishAndRemoveTask()
    }

    private fun saveInternetStatePref(storage: Boolean) {
        providesSharedPrefStored(this, net_connectivity, storage)
    }
}
