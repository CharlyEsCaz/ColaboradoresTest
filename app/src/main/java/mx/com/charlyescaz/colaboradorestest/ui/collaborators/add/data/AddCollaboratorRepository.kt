package mx.com.charlyescaz.colaboradorestest.ui.collaborators.add.data

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import mx.com.charlyescaz.database.dao.CollaboratorDao
import mx.com.charlyescaz.database.models.CollaboratorDB

class AddCollaboratorRepository(private val collaboratorDao: CollaboratorDao) {

    fun store(collaborator: CollaboratorDB, cb: (success: Boolean) -> Unit): Disposable {
        return collaboratorDao.upsert(collaborator)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { cb(true) },
                { cb(false) }
            )
    }
}