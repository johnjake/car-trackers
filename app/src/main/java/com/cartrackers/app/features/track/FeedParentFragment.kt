package com.cartrackers.app.features.track

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cartrackers.app.databinding.FragmentFeedsParentBinding

class FeedParentFragment: Fragment() {
    private var binding: FragmentFeedsParentBinding? = null
    val bind get() = binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFeedsParentBinding.inflate(inflater, container, false)
        return bind?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}