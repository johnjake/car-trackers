package com.cartrackers.app.features.track

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cartrackers.app.databinding.FragmentFeedBinding

class FeedFragment: Fragment() {
    private var binding: FragmentFeedBinding? = null

    private val bind get() = binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFeedBinding.inflate(inflater, container, false)
        return bind?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}