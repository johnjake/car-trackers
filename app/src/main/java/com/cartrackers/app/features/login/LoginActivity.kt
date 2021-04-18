package com.cartrackers.app.features.login

import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.cartrackers.app.R
import com.cartrackers.app.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val rotation = AnimationUtils.loadAnimation(this, R.anim.anim_rotation)
        binding.imgLogo.startAnimation(rotation)
    }

    override fun onStart() {
        super.onStart()
    }
}