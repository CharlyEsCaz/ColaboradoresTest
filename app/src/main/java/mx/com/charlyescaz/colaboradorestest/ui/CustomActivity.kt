package mx.com.charlyescaz.colaboradorestest.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import mx.com.charlyescaz.colaboradorestest.R
import mx.com.charlyescaz.colaboradorestest.utils.ToolbarUtils

open class CustomActivity:AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    fun setupBackButton(toolbar: Toolbar, color: Int = R.color.colorWhite) {
        ToolbarUtils.setupBackBtn(toolbar, this, color)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true;
    }
}