package mx.com.charlyescaz.colaboradorestest.ui.launch.view.interfaces

import com.google.firebase.auth.FirebaseUser

interface LaunchView {

    fun onSessionCheck(user: FirebaseUser?)
}