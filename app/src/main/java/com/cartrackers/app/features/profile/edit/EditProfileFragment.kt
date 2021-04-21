package com.cartrackers.app.features.profile.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.cartrackers.app.comms.CarDialog
import com.cartrackers.app.data.vo.*
import com.cartrackers.app.databinding.FragmentEditProfileBinding
import com.cartrackers.app.utils.toClassType
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import timber.log.Timber

class EditProfileFragment: Fragment() {
    private var binding: FragmentEditProfileBinding? = null
    private val bind get() = binding
    private val viewModel: ViewModel by inject()
    private var profile: User? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        return bind?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.editedState.observe(viewLifecycleOwner) { state ->
                handleStateFlow(state)
            }
        }
    }

    private fun handleStateFlow(state: State<Int>) {
        when(state) {
            is State.Data -> handleSuccess(state.data)
            is State.Error -> handleFailed(state.error)
            else -> Timber.e("An error occurred during query request!")
        }
    }

    private fun handleFailed(error: Throwable) {
        Timber.e("${error.message}")
    }

    private fun handleSuccess(data: Int) {
        if(data>0) {
            context?.let { CarDialog.builderAlert(it, "Update", "${profile?.name} profile successfully updated!") }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding?.save?.setOnClickListener {
            updateProfile()
        }
    }

    override fun onStart() {
        super.onStart()
        arguments?.let { bundle ->
            val arg = EditProfileFragmentArgs.fromBundle(bundle)
            if(arg.profile.isNotEmpty()) {
                profile = arg.profile.toClassType<User>()
            }
        }
        binding?.nameField?.setText(profile?.name)
        binding?.userNameField?.setText((profile?.username))
        binding?.emailField?.setText(profile?.email)
        binding?.streetAddress?.setText(profile?.address?.street)
        binding?.suiteAddress?.setText(profile?.address?.suite)
        binding?.cityAddress?.setText(profile?.address?.city)
        binding?.zipCode?.setText(profile?.address?.zipcode)
        binding?.phoneNo?.setText(profile?.phone)
        binding?.urlWebsite?.setText(profile?.website)
        binding?.companyName?.setText(profile?.company?.name)
        binding?.catchPhrases?.setText(profile?.company?.catchPhrase)
        binding?.companyBs?.setText(profile?.company?.bs)
    }

    private fun updateProfile() {
       val name = binding?.nameField?.text.toString()
       val email = binding?.emailField?.text.toString()
       val username = binding?.userNameField?.text.toString()
       val street = binding?.streetAddress?.text.toString()
       val suitAdd = binding?.suiteAddress?.text.toString()
       val city = binding?.cityAddress?.text.toString()
       val zip = binding?.zipCode?.text.toString()
       val phone = binding?.phoneNo?.text.toString()
       val url = binding?.urlWebsite?.text.toString()
       val compName = binding?.companyName?.text.toString()
       val phrases = binding?.catchPhrases?.text.toString()
       val compBs = binding?.companyBs?.text.toString()
       val coordinates = Coordinates(profile?.address?.geo?.lat, profile?.address?.geo?.lng)
       val userId = profile?.id ?: 0
       val address = Address(street, suitAdd, city, zip, coordinates)
       val company = Company(compName, phrases, compBs)
        viewModel.updateProfile(userId, name, username, email, address, phone, url, company)
    }
}