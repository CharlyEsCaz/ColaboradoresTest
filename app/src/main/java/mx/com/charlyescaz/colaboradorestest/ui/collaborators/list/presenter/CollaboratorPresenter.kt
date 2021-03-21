package mx.com.charlyescaz.colaboradorestest.ui.collaborators.list.presenter

import android.content.Context
import mx.com.charlyescaz.colaboradorestest.R
import mx.com.charlyescaz.colaboradorestest.models.Collaborator
import mx.com.charlyescaz.colaboradorestest.ui.collaborators.list.data.CollaboratorRepository
import mx.com.charlyescaz.colaboradorestest.ui.collaborators.list.view.interfaces.CollaboratorsView
import mx.com.charlyescaz.colaboradorestest.utils.ModelExtensions.toListModel

class CollaboratorPresenter(
    private val context: Context,
    private val view: CollaboratorsView,
    private val repository: CollaboratorRepository
) {

    fun getCollaboratorsList() {
        view.showProgress()
        repository.getCollaborators { success, data ->
            view.hideProgress()
            if (!success || data == null) {
                view.handleError(context.getString(R.string.collaborators_errors_get))
                return@getCollaborators
            }

            if (data.isEmpty()) {
                view.onEmptyList()
            } else {
                view.onNoEmptyList()
                view.list(data.toListModel(Collaborator::class.java))
            }
        }
    }
}