package mx.com.charlyescaz.colaboradorestest.ui.home.data

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import mx.com.charlyescaz.database.dao.CollaboratorDao
import mx.com.charlyescaz.database.models.CollaboratorDB

class HomeRepository(private val collaboratorDao: CollaboratorDao) {

    fun countLocalCollaborators(cb: (success: Boolean, data: Int?) -> Unit): Disposable {
        return collaboratorDao.count()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { cb(true, it) },
                { cb(false, null) }
            )
    }

    fun storeCollaborator(
        vararg collaborator: CollaboratorDB,
        cb: (success: Boolean) -> Unit
    ): Disposable {
        return collaboratorDao.upsert(*collaborator)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { cb(true) },
                { cb(false) }
            )
    }

    fun logoutFromFb() {
        Firebase.auth.signOut()
    }

}