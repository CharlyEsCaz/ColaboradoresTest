package mx.com.charlyescaz.colaboradorestest.ui.auth.data

import android.content.Context
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import mx.com.charlyescaz.colaboradorestest.R

class LoginRepository(private val context: Context) {
    private val auth = Firebase.auth

    fun googleLogin(
            credential: AuthCredential,
            cb: (success: Boolean, message: String) -> Unit
    ) {
        Firebase.auth.signInWithCredential(credential)
                .addOnCompleteListener { task ->
                    var success = false
                    var message = ""

                    if (!task.isSuccessful) {
                        message = context.getString(R.string.google_error_credentials)
                    } else {
                        success = true
                        message = context.getString(R.string.google_success_credentials)
                    }
                    cb(success, message)
                }
    }


}