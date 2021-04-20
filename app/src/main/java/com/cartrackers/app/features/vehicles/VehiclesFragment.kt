package com.cartrackers.app.features.vehicles

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cartrackers.app.databinding.FragmentVehiclesBinding

class VehiclesFragment: Fragment() {

    private var binding: FragmentVehiclesBinding? = null
    private val bind get() = binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentVehiclesBinding.inflate(inflater, container, false)
        return bind?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}