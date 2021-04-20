package com.cartrackers.app.features.track

import android.os.Bundle
import androidx.fragment.app.Fragment

class VehiclesFragment: Fragment() {

    companion object {
        fun newInstance(filter: String): Fragment {
            val fragment = VehiclesFragment()
            val args = Bundle()
            args.putString(ARGS_FILTER, filter)
            fragment.arguments = args
            return fragment
        }
        private const val ARGS_FILTER = "ARGS_FILTER"
        private const val VISIBLE_THRESHOLD = 1
        private const val THRESHOLD_DELAY = 600L
    }
}