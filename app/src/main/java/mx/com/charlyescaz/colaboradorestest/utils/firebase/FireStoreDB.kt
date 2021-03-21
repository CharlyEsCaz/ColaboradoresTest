package mx.com.charlyescaz.colaboradorestest.utils.firebase

import android.annotation.SuppressLint
import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import mx.com.charlyescaz.database.dao.CollaboratorDao
import mx.com.charlyescaz.database.models.CollaboratorDB

class FireStoreDB(private val collaboratorDao: CollaboratorDao) {
    private val db = Firebase.firestore

    @SuppressLint("CheckResult")
    fun backupData() {
        collaboratorDao.findByInCloud(false)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { collaborators ->
                    for (collaboratorDB in collaborators) {
                        collaboratorDB.inCloud = true
                        backupInFireStore(collaboratorDB)
                    }
                },
                {}
            )
    }

    private fun backupInFireStore(collaborator: CollaboratorDB) {
        db.collection("collaborators")
            .add(collaborator)
            .addOnSuccessListener { _ ->
                collaboratorDao.upsert(collaborator)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({}, {})
            }
            .addOnFailureListener { e ->
                Log.w(this::class.java.name, "An error has occurred", e)
            }
    }
}