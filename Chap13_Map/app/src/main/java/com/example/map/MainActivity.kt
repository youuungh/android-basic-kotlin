package com.example.map

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.map.databinding.ActivityMainBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MainActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var binding: ActivityMainBinding
    private lateinit var map: GoogleMap
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //(supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?)!!.getMapAsync(this)
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap;

        val latLng = LatLng(37.4963288792, 127.028785944)
        val position: CameraPosition = CameraPosition.Builder()
            .target(latLng)
            .zoom(16f)
            .build()

//        val markerOptions = MarkerOptions()
//        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
//        markerOptions.position(latLng)
//        markerOptions.title("MyLocation")
        map.addMarker(MarkerOptions().position(latLng).title("내 위치"))
        map.moveCamera(CameraUpdateFactory.newCameraPosition(position))
    }
}

