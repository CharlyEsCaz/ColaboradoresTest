package mx.com.charlyescaz.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
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

    abstract fun collaboratorDao(): CollaboratorDao

    companion object {
        @Volatile
        lateinit var db: DBColaboradores

        fun createDatabase(context: Context) {
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DBColaboradores::class.java,
                    "DBColaboradores.db"
                ).fallbackToDestructiveMigration()
                    .build()
                db = instance
                instance
            }
        }
    }
}