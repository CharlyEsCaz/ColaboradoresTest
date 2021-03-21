package mx.com.charlyescaz.colaboradorestest.ui.collaborators.add.presenter

import android.content.Context
import mx.com.charlyescaz.colaboradorestest.R
import mx.com.charlyescaz.colaboradorestest.models.Collaborator
import mx.com.charlyescaz.colaboradorestest.ui.collaborators.add.data.AddCollaboratorRepository
import mx.com.charlyescaz.colaboradorestest.ui.collaborators.add.view.interfaces.AddCollaboratorView
import mx.com.charlyescaz.colaboradorestest.utils.ModelExtensions.toCollaboratorDB

class AddCollaboratorPresenter(
    private val view: AddCollaboratorView,
    private val context: Context,
    private val repository: AddCollaboratorRepository
) {

    fun storePartner(collaborator: Collaborator) {
        val collaboratorDb = collaborator.toCollaboratorDB()
        view.showProgress()

        repository.store(collaboratorDb) { success ->
            view.hideProgress()
            if (!success) {
                view.handleError(context.getString(R.string.error_insert_collaborator))
                return@store
            }

            view.onInsertSuccess()
        }
    }
}