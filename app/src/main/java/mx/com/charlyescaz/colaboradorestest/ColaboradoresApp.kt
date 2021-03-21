package mx.com.charlyescaz.colaboradorestest

import android.app.Application
import androidx.room.Room
import mx.com.charlyescaz.database.DBColaboradores

class ColaboradoresApp: Application() {

    override fun onCreate() {
        super.onCreate()
        initDatabase()
    }


    private fun initDatabase() {
        DBColaboradores.db = Room
            .databaseBuilder(this,
                DBColaboradores::class.java,
                "colaboradores.db")
            .fallbackToDestructiveMigration()
            .build()
    }
}