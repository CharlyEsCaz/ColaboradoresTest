package mx.com.charlyescaz.colaboradorestest.ui.auth.view.interfaces

import com.google.firebase.auth.FirebaseUser

interface LoginView {

    fun handleLoginSuccess()

    fun handleLoginError(message: String)

    fun showProgress()

    fun hideProgress()
}