package mx.com.charlyescaz.colaboradorestest.ui.collaborators.add.view.interfaces

interface AddCollaboratorView {
    fun handleError(message: String?)

    fun showProgress()

    fun hideProgress()

    fun onInsertSuccess()
}