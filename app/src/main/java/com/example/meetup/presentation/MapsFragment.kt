package com.example.meetup.presentation

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.meetup.R
import com.example.meetup.model.FirestoreUser

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class MapsFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMarkerDragListener {
    private val args: MapsFragmentArgs by navArgs()
    private val user by lazy {
        arguments?.getParcelable("mKey") as? FirestoreUser
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        val geo = args.userObject.address?.geo
        val lat = geo?.lat!!
        val lng = geo.lng!!
        val userLocation = LatLng(lat, lng)
        googleMap?.addMarker(
            MarkerOptions()
                .position(userLocation)
                .draggable(true)
                .title("Marker in Sydney")
        )

        googleMap?.setOnMarkerDragListener(this)

        val cameraPosition = CameraPosition.Builder()
            .target(userLocation) // Sets the center of the map to Mountain View
            .zoom(8f)            // Sets the zoom
            .bearing(90f)         // Sets the orientation of the camera to east
            .tilt(30f)            // Sets the tilt of the camera to 30 degrees
            .build()              // Creates a CameraPosition from the builder
        googleMap?.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))

    }

    override fun onMarkerDragStart(p0: Marker?) = Unit

    override fun onMarkerDrag(p0: Marker?) = Unit

    override fun onMarkerDragEnd(marker: Marker?) {
        val lat = marker?.position?.latitude
        val lng = marker?.position?.longitude
        println("### Lat: $lat LNG: $lng")
        updateUser(lat, lng)
    }

    private fun updateUser(lat: Double?, lng: Double?) {
        user?.let {
            it.address?.geo?.lat = lat
            it.address?.geo?.lng = lng
        }
    }
}