package mx.com.charlyescaz.colaboradorestest.ui.home.presenter

import android.content.Context
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import mx.com.charlyescaz.colaboradorestest.R
import mx.com.charlyescaz.colaboradorestest.ui.home.data.HomeRepository
import mx.com.charlyescaz.colaboradorestest.ui.home.view.interfaces.HomeView
import mx.com.charlyescaz.colaboradorestest.utils.files.FileHelper
import mx.com.charlyescaz.colaboradorestest.utils.files.FileUtils
import mx.com.charlyescaz.colaboradorestest.utils.files.NetworkConnection
import mx.com.charlyescaz.colaboradorestest.utils.firebase.FireStoreDB
import mx.com.charlyescaz.database.models.CollaboratorDB
import mx.com.charlyescaz.web.api.APIColaboradores
import java.io.File

class HomePresenter(
    private val view: HomeView,
    private val context: Context,
    private val repository: HomeRepository,
    private val fbHelper: FireStoreDB,
    private val api: APIColaboradores
) {

    private val tempFiles = ArrayList<File>()

    fun downloadCollaborators() {
        view.showProgress()
        //Check local
        repository.countLocalCollaborators { success, data ->
            if (!success) {
                view.hideProgress()
                view.showErrorMessage(context.getString(R.string.collaborators_errors_get))
                return@countLocalCollaborators
            }
            // Count local partners
            if (data == 0) {
                downloadData()
            } else {
                view.hideProgress()
                view.handleDataSuccess()
                fbHelper.backupData()
            }
        }
    }

    private fun downloadData() {

        if (!NetworkConnection.checkAvailability(context)) {
            view.hideProgress()
            view.showErrorMessage(context.getString(R.string.network_error))
            return
        }

        api.getCollaborators { success, data ->
            if (!success) {
                handleError(context.getString(R.string.api_download_error))
                return@getCollaborators
            }
            if (data?.data?.file == null) {
                handleError(context.getString(R.string.api_download_error))
                return@getCollaborators
            }


            FileHelper(data.data?.file!!, "collaborators.zip", context)
                .attemptDownload { file ->

                    if (file == null) {
                        handleError(context.getString(R.string.api_download_error))
                        return@attemptDownload
                    }

                    tempFiles.add(file)
                    decompressFile(file) { filesDecompress ->
                        if (filesDecompress == null) {
                            handleError(context.getString(R.string.collaborators_errors_decompress))
                            return@decompressFile
                        }

                        tempFiles.addAll(filesDecompress)

                        val jsonString = FileUtils.loadJSONFromAsset(filesDecompress.first())
                        val json = Gson().fromJson(jsonString, JsonObject::class.java)
                        storeCollaborators(json["data"].asJsonObject["employees"])
                    }
                }
        }
    }

    private fun decompressFile(file: File, cb: (List<File>?) -> Unit) {
        val files = FileUtils.unzip( file,context )
        cb(files)
    }

    private fun storeCollaborators(collaborators: JsonElement) {
        val gson = Gson()
        val collaboratorsArray = collaborators.asJsonArray.map { gson.fromJson(it, CollaboratorDB::class.java) }
        repository.storeCollaborator(*collaboratorsArray.toTypedArray()) { success ->
            if (!success) {
                handleError(context.getString(R.string.collaborators_errors_decompress))
                return@storeCollaborator
            }
            view.hideProgress()
            view.handleDataSuccess()
            deleteFiles()
            fbHelper.backupData()
        }
    }

    private fun handleError(message: String) {
        view.hideProgress()
        view.showErrorMessage(message)
        deleteFiles()
    }

    private fun deleteFiles() {
        for (file in tempFiles) {
            file.delete()
        }
    }

    fun logout() {
        repository.logoutFromFb()
        view.onLogout()
    }

}