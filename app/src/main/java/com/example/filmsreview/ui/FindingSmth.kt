package com.example.filmsreview.ui

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts

import androidx.core.content.ContextCompat
import com.example.filmsreview.R
import com.example.filmsreview.databinding.FragmentFindingSmthBinding
import kotlinx.coroutines.*
import java.io.IOException
import java.util.jar.Manifest

class FindingSmth : Fragment(), CoroutineScope by MainScope() {

    private var _binding: FragmentFindingSmthBinding? = null
    private val binding get() = _binding!!

    private val permissionResult = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { result ->
        if (result) {
            getLocation()
        } else {
            Toast.makeText(
                context,
                getString(R.string.need_permission_to_find_location),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFindingSmthBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkPermission()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDestroy() {
        cancel()
        super.onDestroy()
    }

    private fun checkPermission() {
        activity?.let {
            when {
                ContextCompat.checkSelfPermission(
                    it,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ) ==
                        PackageManager.PERMISSION_GRANTED -> {
                    getLocation()
                }
                else ->
                    permissionResult.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLocation() {
        val locationManager =
            requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager

        if (locationManager.isProviderEnabled((LocationManager.GPS_PROVIDER))) {
            val provider = locationManager.getProvider((LocationManager.GPS_PROVIDER))
            provider?.let {
                locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    5000L,
                    10f,
                    onLocationListener
                )
            }
        } else {
            val location =
                locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            if (location == null) {
                Toast.makeText(
                    requireContext(), getString(R.string.looks_like_location_disabled),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                getAdressAsync(location)
            }
        }
    }

    private val onLocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            getAdressAsync(location)
        }

        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }

    private fun getAdressAsync(location: Location) {
        val geoCoder = Geocoder(context)

        launch(Dispatchers.IO) {
            try {
                val adresses = geoCoder.getFromLocation(
                    location.latitude,
                    location.longitude,
                    1
                )
                //смена потока на UI, чтобы потом показать диалог
                withContext(Dispatchers.Main) {
                    showAdressDioalog(adresses[0].getAddressLine(0), location)
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }


    private fun showAdressDioalog(address: String?, location: Location) {
        activity?.let {
            androidx.appcompat.app.AlertDialog.Builder(it)
                .setTitle(getString(R.string.dialog_address_title))
                .setMessage(address)
                .setCancelable(true)
                .setNeutralButton(getString(R.string.dialog_address_ok)) { _, _ ->
                }
                .create()
                .show()
        }
    }


}