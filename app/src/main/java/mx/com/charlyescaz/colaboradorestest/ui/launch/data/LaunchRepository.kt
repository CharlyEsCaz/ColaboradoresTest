package mx.com.charlyescaz.colaboradorestest.ui.launch.data

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LaunchRepository{
    private val auth = Firebase.auth

    fun checkUser(cb: (FirebaseUser?) -> Unit) {
        cb(auth.currentUser)
    }
}