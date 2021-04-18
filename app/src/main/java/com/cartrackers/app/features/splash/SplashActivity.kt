package com.cartrackers.app.features.splash

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cartrackers.app.BuildConfig
import com.cartrackers.app.databinding.ActivitySplashBinding
import com.cartrackers.app.features.login.LoginActivity
import com.cartrackers.app.utils.alert_dialog.ListenerCallBack
import com.cartrackers.app.utils.alert_dialog.TrackerAlertDialog
import com.cartrackers.app.utils.isOnline
import com.cartrackers.app.utils.local_storage
import com.cartrackers.app.utils.shared_pref
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity: AppCompatActivity() {
    private val scope = CoroutineScope(Dispatchers.IO)

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        when(this.isOnline(this)) {
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
        val packageName = BuildConfig.APPLICATION_ID
        startActivity(Intent(this, LoginActivity::class.java).apply {
            putExtra("INTERNET", "1")
        })
    }

    private fun verifyConnection() {
        val alertDialog = TrackerAlertDialog()
        alertDialog.alertInitialize(
            this,
            "No Internet",
            "Would you like to switch to offline?",
            Typeface.SANS_SERIF,
            Typeface.DEFAULT_BOLD,
            isCancelable = true,
            isNegativeBtnHide = false)
        alertDialog.setPositive("YES", object : ListenerCallBack {
            override fun onClick(dialog: TrackerAlertDialog) {
                saveInternetStatePref(true)
                launchActivity()
                dialog.dismiss()
            }
        })
        alertDialog.setNegative("NO", object : ListenerCallBack {
            override fun onClick(dialog: TrackerAlertDialog) {
                dialog.dismiss()
                exitApp()
            }
        })
        alertDialog.show()
    }

    private fun exitApp() {
        finishAndRemoveTask()
    }

    private fun saveInternetStatePref(storage: Boolean) {
        val pref = this.getSharedPreferences(
            shared_pref,
            AppCompatActivity.MODE_PRIVATE
        )
        val editor = pref?.edit()
        editor?.putBoolean(local_storage, storage)
        editor?.apply()
    }
}