package com.cartrackers.app.features.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cartrackers.app.databinding.ActivityTrackersBinding

class TrackerActivity: AppCompatActivity() {
    private lateinit var binding: ActivityTrackersBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTrackersBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}