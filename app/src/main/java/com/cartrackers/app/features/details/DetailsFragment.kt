package com.cartrackers.app.features.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cartrackers.app.databinding.FragementDetailsBinding

class DetailsFragment: Fragment() {
    private var binding: FragementDetailsBinding? = null

    private val bind get() = binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragementDetailsBinding.inflate(inflater, container, false)
        return bind?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}