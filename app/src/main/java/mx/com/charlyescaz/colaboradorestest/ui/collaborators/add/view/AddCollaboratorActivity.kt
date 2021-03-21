package mx.com.charlyescaz.colaboradorestest.ui.collaborators.add.view

import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.type.LatLng
import mx.com.charlyescaz.colaboradorestest.R
import mx.com.charlyescaz.colaboradorestest.databinding.ActivityAddCollaboratorBinding
import mx.com.charlyescaz.colaboradorestest.models.Collaborator
import mx.com.charlyescaz.colaboradorestest.models.Location
import mx.com.charlyescaz.colaboradorestest.ui.CustomActivity
import mx.com.charlyescaz.colaboradorestest.ui.collaborators.add.data.AddCollaboratorRepository
import mx.com.charlyescaz.colaboradorestest.ui.collaborators.add.presenter.AddCollaboratorPresenter
import mx.com.charlyescaz.colaboradorestest.ui.collaborators.add.view.interfaces.AddCollaboratorView
import mx.com.charlyescaz.colaboradorestest.utils.Dialogs
import mx.com.charlyescaz.colaboradorestest.utils.dialogs.ProgressDialog
import mx.com.charlyescaz.colaboradorestest.utils.firebase.FireStoreDB
import mx.com.charlyescaz.colaboradorestest.utils.maps.GMapHelper
import mx.com.charlyescaz.colaboradorestest.utils.maps.MapInterface
import mx.com.charlyescaz.database.DBColaboradores

class AddCollaboratorActivity : CustomActivity(), AddCollaboratorView, MapInterface {

    private lateinit var vBind: ActivityAddCollaboratorBinding
    private lateinit var presenter: AddCollaboratorPresenter
    private lateinit var gMapHelper: GMapHelper
    private lateinit var progressDialog: ProgressDialog

    private var collaborator = Collaborator()
    private var location = Location()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vBind = DataBindingUtil.setContentView(this, R.layout.activity_add_collaborator)
        vBind.collaborator = collaborator
        presenter = AddCollaboratorPresenter(
            this,
            this,
            AddCollaboratorRepository(DBColaboradores.db.collaboratorDao())
        )
        gMapHelper = GMapHelper(this, this)

        setupMap()
        setupSave()
        super.setupBackButton(vBind.iToolbar.toolbar, R.color.colorWhite)
    }

    private fun setupMap() {
        val mapFragment =
            supportFragmentManager.findFragmentById(R.id.fr_map) as SupportMapFragment?
        mapFragment!!.getMapAsync(gMapHelper)

    }

    private fun setupOnPositionClick(gMap: GoogleMap?) {
        gMap?.setOnMapClickListener { position ->
            location.lat = position.latitude
            location.log = position.longitude

            gMapHelper.centerPosition(position)
            gMapHelper.setUniqueMarker(position, getString(R.string.address))

        }
    }

    private fun setupSave() {
        vBind.btnSave.setOnClickListener {
            if (validate()) {
                collaborator.location = location
                presenter.storePartner(collaborator)
            }
        }
    }

    private fun validate(): Boolean {
        var valid = true
        vBind.tilName.error = null
        vBind.tilMail.error = null


        if (vBind.etName.text.toString().trim() == "") {
            vBind.tilName.error = getString(R.string.required)
            valid = false
        }

        if (vBind.etMail.text.toString().trim() == "") {
            vBind.tilMail.error = getString(R.string.required)
            valid = false
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(vBind.tilMail.editText?.text!!)
                .matches()
        ) {
            vBind.tilMail.error = getString(R.string.error_email)
            valid = false
        }

        return valid
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

    override fun onInsertSuccess() {
        FireStoreDB(DBColaboradores.db.collaboratorDao()).backupData()
        Toast.makeText(this, getString(R.string.insert_success), Toast.LENGTH_LONG).show()
        finish()
    }

    override fun onMapCompletelyConfigured(gMap: GoogleMap?) {
        //Default Location on Polanco
        val locationDef = com.google.android.gms.maps.model.LatLng(
            19.434859654965788,
            -99.19565452757367
        )

        gMapHelper.centerPosition(locationDef)
        gMapHelper.setMarker(locationDef, getString(R.string.address))

        location.lat = 19.434859654965788
        location.log = -99.19565452757367

        setupOnPositionClick(gMap!!)
    }
}