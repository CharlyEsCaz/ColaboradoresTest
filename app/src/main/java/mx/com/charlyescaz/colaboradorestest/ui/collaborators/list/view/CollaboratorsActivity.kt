package mx.com.charlyescaz.colaboradorestest.ui.collaborators.list.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Adapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import mx.com.charlyescaz.colaboradorestest.R
import mx.com.charlyescaz.colaboradorestest.databinding.ActivityCollaboratorsBinding
import mx.com.charlyescaz.colaboradorestest.models.Collaborator
import mx.com.charlyescaz.colaboradorestest.ui.CustomActivity
import mx.com.charlyescaz.colaboradorestest.ui.collaborators.list.data.CollaboratorRepository
import mx.com.charlyescaz.colaboradorestest.ui.collaborators.list.presenter.CollaboratorPresenter
import mx.com.charlyescaz.colaboradorestest.ui.collaborators.list.view.adapter.CollaboratorAdapter
import mx.com.charlyescaz.colaboradorestest.ui.collaborators.list.view.interfaces.CollaboratorsView
import mx.com.charlyescaz.colaboradorestest.ui.collaborators.map.view.MapActivity
import mx.com.charlyescaz.database.DBColaboradores

class CollaboratorsActivity : CustomActivity(), CollaboratorsView {

    private lateinit var vBind: ActivityCollaboratorsBinding
    private lateinit var presenter: CollaboratorPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vBind = DataBindingUtil.setContentView(this, R.layout.activity_collaborators)
        presenter = CollaboratorPresenter(
            this,
            this,
            CollaboratorRepository(DBColaboradores.db.collaboratorDao())
        )

        presenter.getCollaboratorsList()
        setupMapView()
        super.setupBackButton(vBind.iToolbar.toolbar, R.color.colorWhite)
    }

    private fun setupMapView() {
        vBind.cvMap.setOnClickListener {
            startActivity(Intent(this, MapActivity::class.java))
        }
    }

    private fun setupAdapter(collaborators: List<Collaborator>) {
        val adapter = CollaboratorAdapter(collaborators) { collaborator ->
            val intent = Intent(this, MapActivity::class.java)
            intent.putExtra(MapActivity.COLLABORATOR_ID, collaborator.idLocalDB)
            startActivity(intent)
        }

        vBind.rvCollaborators.adapter = adapter
    }

    private fun setupRecyclerView() {
        vBind.rvCollaborators.layoutManager = LinearLayoutManager(this)
        vBind.rvCollaborators.itemAnimator = DefaultItemAnimator()
    }

    override fun list(collaborators: List<Collaborator>) {
        setupRecyclerView()
        setupAdapter(collaborators)
    }

    override fun handleError(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun showProgress() {
        vBind.pbLoading.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        vBind.pbLoading.visibility = View.GONE
    }

    override fun onEmptyList() {
        vBind.lblEmpty.visibility = View.VISIBLE
    }

    override fun onNoEmptyList() {
        vBind.lblEmpty.visibility = View.GONE
    }


}