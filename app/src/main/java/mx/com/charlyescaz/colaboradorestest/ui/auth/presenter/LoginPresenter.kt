package mx.com.charlyescaz.colaboradorestest.ui.auth.presenter

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.GoogleAuthProvider
import mx.com.charlyescaz.colaboradorestest.ui.auth.data.FirebaseRepos
import mx.com.charlyescaz.colaboradorestest.ui.auth.view.interfaces.LoginView

class LoginPresenter(
    private val view: LoginView,
    private val context: Context,
    private val fbRepos: FirebaseRepos
) {

    fun attemptLoginGoogle(task: Task<GoogleSignInAccount>)
    {
        view.showProgress()
        try{
            val account = task.getResult(ApiException::class.java)
            val credential = GoogleAuthProvider.getCredential(account?.idToken!!, null)

            fbRepos.googleLogin(credential){ success, message ->
                view.hideProgress()
                if(!success){
                    view.handleLoginError(message)
                }

                view.handleLoginSuccess()
            }
        }catch (e: ApiException){
            view.handleLoginError("Error")
        }

    }
}