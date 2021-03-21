package mx.com.charlyescaz.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import mx.com.charlyescaz.database.models.CollaboratorDB

@Dao
interface CollaboratorDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(vararg collaborator: CollaboratorDB): Completable

    @Query("SELECT * FROM collaborator")
    fun getAll(): Maybe<List<CollaboratorDB>>

    @Query("SELECT COUNT(*) FROM collaborator")
    fun count(): Single<Int>

    @Query("SELECT * FROM collaborator WHERE in_cloud = :backup")
    fun findByInCloud(backup: Boolean): Maybe<List<CollaboratorDB>>

    @Query("SELECT * FROM collaborator WHERE idLocalDB = :idLocal")
    fun findByLocalId(idLocal: Long): Single<CollaboratorDB>

    @Query("DELETE FROM collaborator")
    fun deleteAll(): Completable
}