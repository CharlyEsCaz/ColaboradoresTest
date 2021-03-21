package mx.com.charlyescaz.colaboradorestest.utils.maps

import com.google.android.gms.maps.GoogleMap

interface MapInterface {
    fun onMapCompletelyConfigured(gMap: GoogleMap?)
}