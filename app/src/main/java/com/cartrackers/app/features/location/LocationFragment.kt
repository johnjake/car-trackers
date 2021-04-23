package com.cartrackers.app.features.location

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.cartrackers.app.R
import com.cartrackers.app.data.vo.User
import com.cartrackers.app.databinding.FragmentLocationBinding
import com.cartrackers.app.extension.hideNavigation
import com.cartrackers.app.extension.toAvatar
import com.cartrackers.app.extension.toClassType
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import timber.log.Timber
import java.util.*

class LocationFragment: Fragment(), OnMapReadyCallback {
    private var binding: FragmentLocationBinding? = null
    private lateinit var mMap: GoogleMap
    private val bind get() = binding
    private var profile: User? = null
    private var contExt: Context? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLocationBinding.inflate(inflater, container, false)
        contExt = binding?.root?.context
        return bind?.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment = childFragmentManager.findFragmentById(R.id.mapContainer) as SupportMapFragment
        mapFragment.getMapAsync(this)

        arguments?.let { bundle: Bundle ->
            val args = LocationFragmentArgs.fromBundle(bundle)
            profile = args.userProfile.toClassType()
        }
        val userId = profile?.id ?: 0

        contExt?.let { binding?.avatar?.toAvatar(userId, it) }
        binding?.profileName?.text = profile?.name
        binding?.carTrack?.text = "UserId: ${profile?.id}"

        binding?.avatar?.setOnClickListener {
            val args = LocationFragmentDirections.actionLocationProfile(userId)
            it.findNavController().navigate(args)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.hideNavigation()
    }

    @SuppressLint("SetTextI18n")
    override fun onStart() {
        super.onStart()

        binding?.backButton?.setOnClickListener {
            it.findNavController().popBackStack()
        }
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        if (googleMap != null) {
            mMap = googleMap
        }
        val coordinates = profile?.address?.geo
        val lat = coordinates?.lat ?: 0.0
        val lng = coordinates?.lng ?: 0.0
        val location = LatLng(lat, lng)
        val zoomLevel = 15f

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, zoomLevel))
        mMap.addMarker(MarkerOptions().position(location))

        setMapLongClick(mMap)
        setPoiClick(mMap)
        setMapStyle(mMap)
        enableMyLocation()
    }

    private fun setMapLongClick(map: GoogleMap) {
        map.setOnMapLongClickListener { latLng ->
            val snippet = String.format(
                Locale.getDefault(),
                "Lat: %1$.5f, Long: %2$.5f",
                latLng.latitude,
                latLng.longitude
            )
            map.addMarker(
                MarkerOptions()
                    .position(latLng)
                    .title(getString(R.string.dropped_pin))
                    .snippet(snippet)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
            )
        }
    }

    private fun setPoiClick(map: GoogleMap) {
        map.setOnPoiClickListener { poi ->
            val poiMarker = map.addMarker(
                MarkerOptions()
                    .position(poi.latLng)
                    .title(poi.name)
            )
            poiMarker.showInfoWindow()
        }
    }

    private fun setMapStyle(map: GoogleMap) {
        try {
            val success = map.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                    contExt,
                    R.raw.map_style
                )
            )
            if (!success) {
                Timber.e("Style parsing failed.")
            }
        } catch (e: Resources.NotFoundException) {
            Timber.e( "Can't find style. Error: ")
        }
    }

    private fun isPermissionGranted() : Boolean {
        return contExt?.let {
            ContextCompat.checkSelfPermission(
                it,
                Manifest.permission.ACCESS_FINE_LOCATION)
        } == PackageManager.PERMISSION_GRANTED
    }

    private fun enableMyLocation() {
        if (isPermissionGranted()) {
            if (context?.let {
                    ActivityCompat.checkSelfPermission(
                        it,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    )
                } != PackageManager.PERMISSION_GRANTED && context?.let {
                    ActivityCompat.checkSelfPermission(
                        it,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                } != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            mMap.isMyLocationEnabled = true
        }
        else {
            activity?.let {
                ActivityCompat.requestPermissions(
                    it,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    REQUEST_LOCATION_PERMISSION
                )
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray) {
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.contains(PackageManager.PERMISSION_GRANTED)) {
                enableMyLocation()
            }
        }
    }
    companion object {
        const val REQUEST_LOCATION_PERMISSION = 1
    }
}