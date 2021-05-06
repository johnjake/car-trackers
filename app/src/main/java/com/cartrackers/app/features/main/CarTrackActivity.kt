package com.cartrackers.app.features.main

import android.os.Bundle
import android.view.MenuItem
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.cartrackers.app.R
import com.cartrackers.app.comms.CarDialog
import com.cartrackers.app.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.flow.MutableStateFlow

class CarTrackActivity: AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var bottomNavView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupNavigationMenu()
    }

    private fun setupNavigationMenu(){
        bottomNavView = binding.bottomNavigation.apply {
            itemIconTintList = null
        }
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        bottomNavView.setOnNavigationItemSelectedListener(object :
            BottomNavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(@NonNull item: MenuItem): Boolean {
                when (item.itemId) {
                    R.id.feeds_stack -> {
                        navController.navigate(R.id.feedFragment)
                        return true
                    }
                    R.id.car_stack -> {
                        navController.navigate(R.id.carsFragment)
                        return true
                    }
                    R.id.inbox_stack -> {
                        navController.navigate(R.id.inboxFragment)
                        return true
                    }
                    R.id.profile_stack -> {
                        navController.navigate(R.id.movieFragment)
                        // CarDialog.builderAlert(binding.root.context, "Under Construction", "Sorry this page is under-construction")
                        return true
                    }
                }
                return true
            }
        })
    }

    companion object {
        val onBackPress = MutableStateFlow(false)
    }
}
