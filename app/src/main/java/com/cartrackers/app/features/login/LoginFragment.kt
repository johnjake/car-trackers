package com.cartrackers.app.features.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cartrackers.app.R
import com.cartrackers.app.comms.CarDialog
import com.cartrackers.app.comms.EmailAddress
import com.cartrackers.app.databinding.FragmentLoginBinding
import com.cartrackers.app.extension.toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.Job
import java.lang.Exception

class LoginFragment: Fragment() {
    private var binding: FragmentLoginBinding? = null
    private val bind get() = binding
    private val stateJob: Job? = null
    private var isEmailFormat: Boolean = false
    private var isPasswordFormat: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return bind?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right)
        hideNavigation()
    }
git
    override fun onStart() {
        super.onStart()
        validateInputEmail()
        validateAndLogin()
    }

    private fun validateAndLogin() {
        binding?.userLoginButton?.setOnClickListener {
            validateInputPassword()
            if(isEmailFormat && isPasswordFormat) {
                activity?.toast("begin login process")
            }
        }
    }

    private fun validateInputEmail() {
        val textEmail = binding?.email?.text
        binding?.email?.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                if (textEmail.isNullOrEmpty()) {
                    isEmailFormat = false
                    activity?.toast("Email address must not be empty")
                } else {
                    isEmailFormat = try {
                        EmailAddress(textEmail.toString()).toString()
                        true
                    } catch (ex: Exception) {
                        context?.let { CarDialog.builderAlert(it, "Invalid Email", "${ex.message}") }
                        false
                    }
                }
            }
        }
    }

    private fun validateInputPassword() {
        val textPassword = binding?.password?.text
        when {
            textPassword.isNullOrEmpty() -> {
                isPasswordFormat = false
            }
            textPassword.length < 4 -> {
                isPasswordFormat = false
                context?.let { CarDialog.builderAlert(it, "Invalid Password", "Password does not meet required character length") }
            }
            else -> {
                isPasswordFormat = true
            }
        }
    }

    private fun hideNavigation() {
        val bottomNavigationView = activity?.findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNavigationView?.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
        stateJob?.cancel()
    }
}