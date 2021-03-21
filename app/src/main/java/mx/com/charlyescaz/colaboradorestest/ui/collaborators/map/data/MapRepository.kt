package mx.com.charlyescaz.colaboradorestest.ui.collaborators.map.data

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import mx.com.charlyescaz.database.dao.CollaboratorDao
import mx.com.charlyescaz.database.models.CollaboratorDB

class MapRepository(private val collaboratorDao: CollaboratorDao) {

    fun getCollaborators(cb: (success: Boolean, data: List<CollaboratorDB>?) -> Unit): Disposable? {
        return collaboratorDao.getAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { cb(true, it) },
                { cb(false, null) })
    }

    fun getCollaborator(localId: Long, cb: (success: Boolean, data: CollaboratorDB?) -> Unit): Disposable? {
        return collaboratorDao.findByLocalId(localId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { cb(true, it) },
                { cb(false, null) })
    }
}