package mx.com.charlyescaz.colaboradorestest.utils.maps

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.gson.Gson
import mx.com.charlyescaz.colaboradorestest.R
import mx.com.charlyescaz.colaboradorestest.databinding.ItemInfoWindowBinding
import mx.com.charlyescaz.colaboradorestest.models.Collaborator

class GMapHelper(
    private val activity: Activity,
    private val mapHandler: MapInterface
) : OnMapReadyCallback {

    lateinit var map: GoogleMap

    override fun onMapReady(gMap: GoogleMap?) {
        map = gMap!!
        mapHandler.onMapCompletelyConfigured(map)
        setupInfoWindow()
    }

    fun setMarker(
        location: LatLng,
        title: String,
        tag: Any? = null
    ) {
        map.addMarker(
            MarkerOptions()
                .position(location)
                .title(title)
        ).tag = tag
    }

    private fun setupInfoWindow() {
        map.uiSettings.isZoomControlsEnabled = true
        map.setInfoWindowAdapter(object : GoogleMap.InfoWindowAdapter {
            override fun getInfoWindow(p0: Marker?): View? {
                return null
            }

            @SuppressLint("SetTextI18n")
            override fun getInfoContents(marker: Marker?): View? {
                return if (marker?.tag == null) {
                    null
                } else {
                    val view = LayoutInflater.from(activity)
                        .inflate(R.layout.item_info_window, null, false)
                    val vBind = ItemInfoWindowBinding.bind(view)
                    val collaborator =
                        Gson().fromJson(marker.tag.toString(), Collaborator::class.java)
                    vBind.tvName.text = collaborator.name
                    vBind.tvEmail.text = collaborator.mail
                    view
                }
            }

        })
    }

    fun centerList(vararg positions: LatLng) {
        val builder = LatLngBounds.Builder()
        for (position in positions) {
            builder.include(position)
        }
        val bounds = builder.build()
        val padding = 300
        val cu = CameraUpdateFactory.newLatLngBounds(bounds, padding)
        map.animateCamera(cu)
    }

    fun centerPosition(position: LatLng) {
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(position, 15f))
    }


}