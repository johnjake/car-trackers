package com.cartrackers.app.features.search

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.cartrackers.app.databinding.FragmentSearchMainBinding
import android.view.MenuInflater
import com.cartrackers.app.R
import com.cartrackers.app.extension.hideNavigation
import com.cartrackers.app.features.main.CarTrackActivity

class TrackSearchFragment: Fragment() {
    private var binding: FragmentSearchMainBinding? = null
    private val bind get() = binding
    lateinit var contxt: Context
    lateinit var array: Array<String>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchMainBinding.inflate(inflater, container, false)
        contxt = bind?.root?.context!!
        return bind?.root

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerForContextMenu(view)
        activity.hideNavigation()
        CarTrackActivity.onBackPress = true
    }

    override fun onCreateContextMenu(
        menu: ContextMenu,
        v: View,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = MenuInflater(v.context)
        inflater.inflate(R.menu.menu_order, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val selectedItemOrder = item.order
        val selectedItemTitle = item.title
        return true
    }
 }

