package com.cartrackers.app.features.country

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cartrackers.app.R
import com.cartrackers.app.databinding.ActivityCountryBinding
import com.cartrackers.app.features.main.CarTrackActivity

class CountryActivity: AppCompatActivity() {
    private lateinit var binding: ActivityCountryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCountryBinding.inflate(layoutInflater)

        overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom)

        setContentView(binding.root)

        binding.countryNextButton.setOnClickListener {
            launchActivity()
        }
    }

    private fun launchActivity() {
        startActivity(Intent(this, CarTrackActivity::class.java).apply {
            putExtra("INTERNET", "1")
        })
    }
}