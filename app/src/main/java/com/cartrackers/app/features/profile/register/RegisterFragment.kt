package com.cartrackers.app.features.profile.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cartrackers.app.comms.CarDialog
import com.cartrackers.app.data.vo.Address
import com.cartrackers.app.data.vo.Company
import com.cartrackers.app.data.vo.Coordinates
import com.cartrackers.app.data.vo.User
import com.cartrackers.app.databinding.FragmentRegisterBinding
import com.cartrackers.app.di.providesSharedPrefGetCount
import com.cartrackers.app.di.providesSharedUserCount
import com.cartrackers.app.extension.toVerifyField
import com.cartrackers.app.extension.shared_user_no
import org.koin.android.ext.android.inject
import java.lang.Exception

class RegisterFragment: Fragment() {
    private var binding: FragmentRegisterBinding? = null
    private val bind get() = binding
    private val viewModel: ViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return bind?.root
    }

    override fun onStart() {

        super.onStart()
        binding?.save?.setOnClickListener { view ->
            try {
                insertUser(view)
            } catch (ex: Exception) {
                context?.let { CarDialog.builderAlert(it, "Error", "${ex.message}") }
            }
        }
        binding?.backButton?.setOnClickListener {
            activity?.finish()
        }

    }

    private fun insertUser(view: View) {
        val count = providesSharedPrefGetCount(view.context, shared_user_no) ?: 0
        val name = binding?.nameField?.text
        val email = binding?.emailField?.text
        val username = binding?.userNameField?.text.toString()
        val password = binding?.passwordField?.text
        val street = binding?.streetAddress?.text.toString()
        val suitAdd = binding?.suiteAddress?.text.toString()
        val city = binding?.cityAddress?.text.toString()
        val zip = binding?.zipCode?.text.toString()
        val phone = binding?.phoneNo?.text.toString()
        val url = binding?.urlWebsite?.text.toString()
        val compName = binding?.companyName?.text.toString()
        val phrases = binding?.catchPhrases?.text.toString()
        val compBs = binding?.companyBs?.text.toString()
        val coordinates = Coordinates(8.2227214, 124.2337087)
        val address = Address(street, suitAdd, city, zip, coordinates)
        val company = Company(compName, phrases, compBs)
        val countEmail = email?.toVerifyField(view.context, "Email") ?: 0
        val countName = name?.toVerifyField(view.context, "Name") ?: 0
        val countPass = password?.toVerifyField(view.context, "Password") ?: 0
        val counter = count + 1
        if (countEmail == 0 || countName == 0 || countPass == 0) {
           return
        } else {
            val user = User(counter,
                name = name.toString(),
                username = username,
                password = password.toString(),
                email = email.toString(),
                address = address,
                phone = phone,
                website = url,
                company = company
            )
            viewModel.insertUserToDB(user)
            context?.let { CarDialog.builderAlert(it, "Success", "${user.name} profile successfully registered!") }
            context?.let { providesSharedUserCount(it, shared_user_no, counter) }
        }
    }
}