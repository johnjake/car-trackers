package com.cartrackers.app.features.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cartrackers.app.R
import com.cartrackers.app.databinding.ActivityMainBinding
import com.cartrackers.app.extension.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class CarTrackActivity: AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var bottomNavView: BottomNavigationView
    private var bundle = Bundle()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupNavigationMenu()
    }

    private fun setupNavigationMenu() {
        bottomNavView = binding.bottomNavigation.apply {
            itemIconTintList = null
        }

        val navGraphIds = listOf(
            R.navigation.feed_stack
        )

        bottomNavView.setupWithNavController(
            navGraphIds = navGraphIds,
            fragmentManager = supportFragmentManager,
            containerId = R.id.host_container,
            intent = intent
        )
    }
}