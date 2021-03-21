package mx.com.charlyescaz.colaboradorestest

import android.app.Application
import mx.com.charlyescaz.database.DBColaboradores

class ColaboradoresApp: Application() {

    override fun onCreate() {
        super.onCreate()
        initDatabase()
    }


    private fun initDatabase() {
        DBColaboradores.createDatabase(this)
    }
}