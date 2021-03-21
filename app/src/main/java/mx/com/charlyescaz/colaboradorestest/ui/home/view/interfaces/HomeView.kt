package mx.com.charlyescaz.colaboradorestest.ui.home.view.interfaces

interface HomeView {

    fun showProgress()

    fun hideProgress()

    fun showErrorMessage(message: String)

    fun handleDataSuccess()

    fun onLogout()
}