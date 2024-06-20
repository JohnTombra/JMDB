package com.smartapps.jmdb.enumeration.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.smartapps.jmdb.R
import com.smartapps.jmdb.databinding.ActivityMapBinding

class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapBinding

    private lateinit var context: Context
    private var latitude: Double = 0.0
    private var longitude: Double = 0.0
        //data
    //zone
    //zone to login
    //location shared
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = this
        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        latitude = intent.extras!!.getDouble("latitude")
        longitude = intent.extras!!.getDouble("longitude")

        Log.d("ACTIVITY","LATLNG $latitude,$longitude")


        binding.panorama.setOnClickListener {
        //    startActivity(Intent(this, StreetViewActivity::class.java).putExtra("latitude",latitude).putExtra("longitude",longitude))
        }

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val location = LatLng(latitude, longitude)
        mMap.addMarker(MarkerOptions().position(location).title("Property Location"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(location))
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 15.0F))
    }

}