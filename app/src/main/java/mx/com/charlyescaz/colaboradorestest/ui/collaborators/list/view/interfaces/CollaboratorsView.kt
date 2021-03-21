package mx.com.charlyescaz.colaboradorestest.ui.collaborators.list.view.interfaces

import mx.com.charlyescaz.colaboradorestest.models.Collaborator

interface CollaboratorsView {

    fun list(collaborators: List<Collaborator>)

    fun handleError(message: String?)

    fun showProgress()

    fun hideProgress()

    fun onEmptyList()

    fun onNoEmptyList()
}