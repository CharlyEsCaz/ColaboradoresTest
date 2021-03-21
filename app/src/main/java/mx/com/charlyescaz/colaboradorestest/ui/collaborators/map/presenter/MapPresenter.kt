package mx.com.charlyescaz.colaboradorestest.ui.collaborators.map.presenter

import android.content.Context
import mx.com.charlyescaz.colaboradorestest.R
import mx.com.charlyescaz.colaboradorestest.models.Collaborator
import mx.com.charlyescaz.colaboradorestest.ui.collaborators.map.data.MapRepository
import mx.com.charlyescaz.colaboradorestest.ui.collaborators.map.view.interfaces.MapView
import mx.com.charlyescaz.colaboradorestest.utils.ModelExtensions.toCollaborator
import mx.com.charlyescaz.colaboradorestest.utils.ModelExtensions.toListModel

class MapPresenter(
    private val context: Context,
    private val view: MapView,
    private val repository: MapRepository
) {
    fun getCollaboratorsList() {
        view.showProgress()
        repository.getCollaborators { success, data ->
            view.hideProgress()
            if (!success || data == null) {
                view.handleError(context.getString(R.string.collaborators_errors_get))
                return@getCollaborators
            }

            view.showMarkers(data.toListModel(Collaborator::class.java))
        }
    }

    fun getCollaboratorById(localId: Long) {
        view.showProgress()
        repository.getCollaborator(localId) { success, data ->
            view.hideProgress()
            if (!success || data == null) {
                view.handleError(context.getString(R.string.collaborators_errors_get))
                return@getCollaborator
            }

            view.showMarker(data.toCollaborator())
        }
    }
}