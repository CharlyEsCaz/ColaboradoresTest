package mx.com.charlyescaz.colaboradorestest.ui.home.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import mx.com.charlyescaz.colaboradorestest.R
import mx.com.charlyescaz.colaboradorestest.databinding.ActivityHomeBinding
import mx.com.charlyescaz.colaboradorestest.ui.auth.view.LoginActivity
import mx.com.charlyescaz.colaboradorestest.ui.collaborators.add.view.AddCollaboratorActivity
import mx.com.charlyescaz.colaboradorestest.ui.collaborators.list.view.CollaboratorsActivity
import mx.com.charlyescaz.colaboradorestest.ui.home.data.HomeRepository
import mx.com.charlyescaz.colaboradorestest.ui.home.presenter.HomePresenter
import mx.com.charlyescaz.colaboradorestest.ui.home.view.interfaces.HomeView
import mx.com.charlyescaz.colaboradorestest.utils.Dialogs
import mx.com.charlyescaz.colaboradorestest.utils.dialogs.ProgressDialog
import mx.com.charlyescaz.colaboradorestest.utils.firebase.FireStoreDB
import mx.com.charlyescaz.database.DBColaboradores
import mx.com.charlyescaz.web.api.APIColaboradores

class HomeActivity: AppCompatActivity(), HomeView {

    private lateinit var vBind: ActivityHomeBinding
    private lateinit var presenter: HomePresenter
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vBind = DataBindingUtil.setContentView(this, R.layout.activity_home)
        presenter = HomePresenter(
            this,
            this,
            HomeRepository(DBColaboradores.db.collaboratorDao()),
            FireStoreDB(DBColaboradores.db.collaboratorDao()),
            APIColaboradores)

        presenter.downloadCollaborators()

        setupLogout()
    }


    private fun setupCollaboratorsList(){
        vBind.cvCollaborators.visibility = View.VISIBLE

        vBind.cvCollaborators.setOnClickListener {
            startActivity( Intent(this, CollaboratorsActivity::class.java) )
        }
    }

    private fun setupAddCollaborator(){
        vBind.cvAdd.visibility = View.VISIBLE

        vBind.cvAdd.setOnClickListener {
            startActivity( Intent(this, AddCollaboratorActivity::class.java) )
        }
    }

    private fun setupLogout(){
        vBind.llLogout.setOnClickListener {
            MaterialAlertDialogBuilder(this)
                .setTitle(getString(R.string.logout))
                .setMessage(getString(R.string.logout_question))
                .setNegativeButton(getString(R.string.accept)) { _, _ ->
                    presenter.logout()
                }
                .setPositiveButton(getString(R.string.cancel)) { _, _ ->
                }
                .show()
        }
    }


    override fun showProgress() {
        progressDialog = Dialogs.progressDialog(supportFragmentManager, getString(R.string.getting_data))
    }

    override fun hideProgress() {
        progressDialog.dismiss()
    }

    override fun showErrorMessage(message: String) {
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show()
    }

    override fun handleDataSuccess() {
        setupCollaboratorsList()
        setupAddCollaborator()
    }

    override fun onLogout() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
        finish()
    }

}