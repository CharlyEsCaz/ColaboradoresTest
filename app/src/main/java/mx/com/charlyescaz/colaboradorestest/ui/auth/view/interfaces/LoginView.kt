package mx.com.charlyescaz.colaboradorestest.ui.auth.view.interfaces

interface LoginView {

    fun handleLoginSuccess()

    fun handleLoginError(message: String)

    fun showProgress()

    fun hideProgress()
}