package mx.com.charlyescaz.colaboradorestest.ui.collaborators.map.view

import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import mx.com.charlyescaz.colaboradorestest.R
import mx.com.charlyescaz.colaboradorestest.databinding.ActivityMapBinding
import mx.com.charlyescaz.colaboradorestest.models.Collaborator
import mx.com.charlyescaz.colaboradorestest.ui.CustomActivity
import mx.com.charlyescaz.colaboradorestest.ui.collaborators.map.data.MapRepository
import mx.com.charlyescaz.colaboradorestest.ui.collaborators.map.presenter.MapPresenter
import mx.com.charlyescaz.colaboradorestest.ui.collaborators.map.view.interfaces.MapView
import mx.com.charlyescaz.colaboradorestest.utils.Dialogs
import mx.com.charlyescaz.colaboradorestest.utils.dialogs.ProgressDialog
import mx.com.charlyescaz.colaboradorestest.utils.maps.GMapHelper
import mx.com.charlyescaz.colaboradorestest.utils.maps.MapInterface
import mx.com.charlyescaz.database.DBColaboradores

class MapActivity : CustomActivity(), MapView, MapInterface {

    companion object {
        const val COLLABORATOR_ID = "COLLABORATOR_ID"
    }

    private lateinit var vBind: ActivityMapBinding
    private lateinit var gMapHelper: GMapHelper
    private lateinit var presenter: MapPresenter
    private lateinit var progressDialog: ProgressDialog

    private var collaboratorId = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vBind = DataBindingUtil.setContentView(this, R.layout.activity_map)
        gMapHelper = GMapHelper(this, this)
        presenter = MapPresenter(
            this,
            this,
            MapRepository(DBColaboradores.db.collaboratorDao())
        )

        setupMap()
        setupValues()
        super.setupBackButton(vBind.iToolbar.toolbar, R.color.colorWhite)
    }

    private fun setupValues() {
        collaboratorId = intent.getLongExtra(COLLABORATOR_ID, 0)
    }

    private fun setupMap() {
        val mapFragment =
            supportFragmentManager.findFragmentById(R.id.fr_map) as SupportMapFragment?
        mapFragment!!.getMapAsync(gMapHelper)
    }

    override fun showMarkers(collaborators: List<Collaborator>) {
        val gson = Gson()
        val locations = collaborators.map { LatLng(it.location?.lat!!, it.location?.log!!) }
        gMapHelper.centerList(*locations.toTypedArray())

        for (collaborator in collaborators) {

            val location = LatLng(collaborator.location?.lat!!, collaborator.location?.log!!)

            gMapHelper.setMarker(
                location, collaborator.name!!,
                gson.toJson(collaborator).toString()
            )
        }
    }

    override fun showMarker(collaborator: Collaborator) {
        val gson = Gson()
        val location = LatLng(collaborator.location?.lat!!, collaborator.location?.log!!)
        gMapHelper.centerPosition(location)

        gMapHelper.setMarker(
            location, collaborator.name!!,
            gson.toJson(collaborator).toString()
        )
    }

    override fun handleError(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun showProgress() {
        progressDialog = Dialogs.progressDialog(supportFragmentManager, getString(R.string.loading))
    }

    override fun hideProgress() {
        progressDialog.dismiss()
    }

    override fun onMapCompletelyConfigured(gMap: GoogleMap?) {
        if (collaboratorId != 0L) {
            presenter.getCollaboratorById(collaboratorId)
        } else {
            presenter.getCollaboratorsList()
        }
    }
}