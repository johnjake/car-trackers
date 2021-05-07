package com.cartrackers.app.features.splash

import android.os.Bundle
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.cartrackers.app.comms.PopupDialog
import com.cartrackers.app.databinding.ActivitySplashBinding
import com.cartrackers.app.di.providesSharedPrefGetCount
import com.cartrackers.app.di.providesSharedPrefStored
import com.cartrackers.app.di.providesSharedUserCount
import com.cartrackers.app.features.intro.IntroActivity
import com.cartrackers.app.extension.isOnline
import com.cartrackers.app.extension.net_connectivity
import com.cartrackers.app.extension.shared_counter
import com.cartrackers.app.extension.toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {
    private val scope = CoroutineScope(Dispatchers.IO)
    private lateinit var binding: ActivitySplashBinding
    private val dialog = PopupDialog( listener = { confirm -> statusConfirmation(confirm) } )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val counter = providesSharedPrefGetCount(binding.root.context, shared_counter)
        if (counter == 0) {
            providesSharedUserCount(this, shared_counter, 0)
        }
    }

    override fun onResume() {
        super.onResume()
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
        dialog.build(this,"No Internet", "Would you like to switch to offline?")
    }

    private fun exitApp() {
        finishAndRemoveTask()
    }

    private fun saveInternetStatePref(storage: Boolean) {
        providesSharedPrefStored(this, net_connectivity, storage)
    }

    private fun statusConfirmation(confirm: Boolean) {
        if (confirm) {
            saveInternetStatePref(true)
            this.toast("successfully set to local storage")
            launchActivity()

        } else {
            saveInternetStatePref(false)
            exitApp()
        }
    }
}
