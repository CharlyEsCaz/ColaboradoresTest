package mx.com.charlyescaz.colaboradorestest.ui.launch.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseUser
import mx.com.charlyescaz.colaboradorestest.R
import mx.com.charlyescaz.colaboradorestest.databinding.ActivityLaunchBinding
import mx.com.charlyescaz.colaboradorestest.ui.auth.view.LoginActivity
import mx.com.charlyescaz.colaboradorestest.ui.home.view.HomeActivity
import mx.com.charlyescaz.colaboradorestest.ui.launch.data.LaunchRepository
import mx.com.charlyescaz.colaboradorestest.ui.launch.presenter.LaunchPresenter
import mx.com.charlyescaz.colaboradorestest.ui.launch.view.interfaces.LaunchView

class LaunchActivity: AppCompatActivity(), LaunchView {

    private lateinit var vBind: ActivityLaunchBinding
    private lateinit var presenter: LaunchPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vBind = DataBindingUtil.setContentView(this, R.layout.activity_launch)
        presenter = LaunchPresenter(this, LaunchRepository())
        presenter.checkSession()
    }

    override fun onSessionCheck(user: FirebaseUser?) {
        if (user != null) {
            startActivity(Intent(this, HomeActivity::class.java))
        }else{
            startActivity(Intent(this, LoginActivity::class.java))
        }
        finish()
    }
}