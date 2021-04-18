package com.cartrackers.app.features.country

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cartrackers.app.R
import com.cartrackers.app.databinding.ActivityCountryBinding

class CountryActivity: AppCompatActivity() {
    private lateinit var binding: ActivityCountryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCountryBinding.inflate(layoutInflater)

        overridePendingTransition(R.anim.trans_fade_in, R.anim.trans_fade_out)

        setContentView(binding.root)
    }
}