package com.cartrackers.app.features.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.cartrackers.app.R
import com.cartrackers.app.comms.CarDialog
import com.cartrackers.app.comms.EmailAddress
import com.cartrackers.app.data.vo.State
import com.cartrackers.app.data.vo.User
import com.cartrackers.app.databinding.FragmentLoginBinding
import com.cartrackers.app.extension.toast
import com.cartrackers.app.features.main.CarTrackActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.Job
import org.koin.android.ext.android.inject
import timber.log.Timber
import java.lang.Exception

class LoginFragment: Fragment() {
    private var binding: FragmentLoginBinding? = null
    private val bind get() = binding
    private var isEmailFormat: Boolean = false
    private var isPasswordFormat: Boolean = false
    private val viewModel: LoginViewModel by inject()

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

        viewModel.userState.observe(viewLifecycleOwner) { state ->
            handleStateFlow(state)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        validateInputEmail()
        validateAndLogin()
    }

    private fun handleStateFlow(state: State<User?>) {
        when(state) {
            is State.Data -> handlesSuccess(state.data)
            is State.Error -> handleFailed(state.error)
            else -> Timber.e("An error occurred during query request!")
        }
    }

    private fun handleFailed(error: Throwable) {
        context?.let { CarDialog.builderAlert(it,
            "Invalid Credential",
            "Invalid email or password!") }
    }

    private fun handlesSuccess(data: User?) {
        if(data!=null) {
            activity?.toast("Welcome ${data.username} !")
            val args = LoginFragmentDirections.actionLoginToMain(data.id ?: 0)
            view?.findNavController()?.navigate(args)
            CarTrackActivity.onBackPress = true
        } else {
            context?.let { CarDialog.builderAlert(it,
                "Credential",
                "Invalid username or password!") }
        }
    }

    private fun validateAndLogin() {
        //TODO add progress bar
        binding?.userLoginButton?.setOnClickListener {
            validateInputPassword()
            if(isEmailFormat && isPasswordFormat) {
                val username = binding?.email?.text.toString().trim()
                val password = binding?.password?.text.toString().trim()
                viewModel.authenticateUser(username, password)
            }
        }
        binding?.registerButton?.setOnClickListener {
            it.findNavController().navigate(R.id.action_to_register)
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
   }
}