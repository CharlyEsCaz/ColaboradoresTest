package mx.com.charlyescaz.colaboradorestest.ui.auth.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import mx.com.charlyescaz.colaboradorestest.R
import mx.com.charlyescaz.colaboradorestest.databinding.ActivityLoginBinding
import mx.com.charlyescaz.colaboradorestest.ui.auth.data.LoginRepository
import mx.com.charlyescaz.colaboradorestest.ui.auth.presenter.LoginPresenter
import mx.com.charlyescaz.colaboradorestest.ui.auth.view.interfaces.LoginView
import mx.com.charlyescaz.colaboradorestest.ui.home.view.HomeActivity
import mx.com.charlyescaz.colaboradorestest.utils.Codes

class LoginActivity: AppCompatActivity(), LoginView {

    private lateinit var vBind: ActivityLoginBinding
    private lateinit var presenter: LoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vBind = DataBindingUtil.setContentView(this, R.layout.activity_login)
        presenter = LoginPresenter(this,this, LoginRepository(this) )

        setupGoogleLogin()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == Codes.GOOGLE_SIG_IN_REQUEST) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            presenter.attemptLoginGoogle(task)
        }
    }

    private fun setupGoogleLogin()
    {
        vBind.llLogin.setOnClickListener {
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build()

            val googleSignInClient = GoogleSignIn.getClient(this, gso)
            val signInIntent = googleSignInClient.signInIntent
            startActivityForResult(signInIntent, Codes.GOOGLE_SIG_IN_REQUEST)
        }
    }

    override fun handleLoginSuccess() {
        startActivity( Intent(this, HomeActivity::class.java) )
        finish()
    }

    override fun handleLoginError(message: String) {
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show()
    }

    override fun showProgress() {
        vBind.pbLoading.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        vBind.pbLoading.visibility = View.GONE
    }
}