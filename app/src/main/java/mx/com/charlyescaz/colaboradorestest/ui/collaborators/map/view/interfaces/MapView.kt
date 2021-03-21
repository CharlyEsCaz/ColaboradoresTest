package mx.com.charlyescaz.colaboradorestest.ui.collaborators.map.view.interfaces

import mx.com.charlyescaz.colaboradorestest.models.Collaborator

interface MapView {

    fun showMarkers(collaborators: List<Collaborator>)

    fun showMarker(collaborator: Collaborator)

    fun handleError(message: String?)

    fun showProgress()

    fun hideProgress()
}