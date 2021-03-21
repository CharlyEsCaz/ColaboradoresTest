package mx.com.charlyescaz.colaboradorestest.utils

import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import mx.com.charlyescaz.colaboradorestest.R

object ToolbarUtils {

    /**
     * @param toolbar
     * @param mActivity
     */
    fun setupBackBtn(
        toolbar: Toolbar?,
        mActivity: AppCompatActivity,
        colorId: Int
    ) {
        mActivity.setSupportActionBar(toolbar)
        val actionBar = mActivity.supportActionBar
        if (actionBar != null) {
            val drawable = ContextCompat.getDrawable(mActivity, R.drawable.ic_back)
            drawable!!.setColorFilter(
                ContextCompat.getColor(mActivity, colorId),
                PorterDuff.Mode.SRC_ATOP
            )
            actionBar.setHomeAsUpIndicator(drawable)
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setDisplayShowHomeEnabled(true)
            actionBar.setDisplayShowTitleEnabled(false)
        }
    }

    fun disabledBackBtn(
        toolbar: Toolbar?,
        mActivity: AppCompatActivity
    ) {
        mActivity.setSupportActionBar(toolbar)
        val actionBar = mActivity.supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false)
            actionBar.setDisplayShowHomeEnabled(false)
            actionBar.setDisplayShowTitleEnabled(false)
        }
    }
}