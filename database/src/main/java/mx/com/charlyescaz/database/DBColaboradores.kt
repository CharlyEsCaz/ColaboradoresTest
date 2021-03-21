package mx.com.charlyescaz.database

import androidx.room.Database
import androidx.room.RoomDatabase
import mx.com.charlyescaz.database.dao.CollaboratorDao
import mx.com.charlyescaz.database.models.CollaboratorDB

@Database(
    entities = [
        CollaboratorDB::class
    ],
    version = 3,
    exportSchema = false
)
abstract class DBColaboradores: RoomDatabase() {

    companion object {
        lateinit var db: DBColaboradores
    }

    abstract fun collaboratorDao(): CollaboratorDao

}