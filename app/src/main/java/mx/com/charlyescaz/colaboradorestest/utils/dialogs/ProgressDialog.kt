package mx.com.charlyescaz.colaboradorestest.utils.dialogs

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import mx.com.charlyescaz.colaboradorestest.R
import java.lang.Exception

/*
 * Copyright 2018 Qi Li
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
class ProgressDialog(private val message: String = "Espere...") : DialogFragment() {

    // default constructor. Needed so rotation doesn't crash


    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder =
            AlertDialog.Builder(activity!!)
        val inflater = activity!!.layoutInflater
        builder.setView(inflater.inflate(R.layout.dialog_progress, null))
        builder.setCancelable(false)
        return builder.create().apply {
            setCancelable(false)
        }
    }


    override fun onStart() {
        super.onStart()
        val tvMessage = dialog!!.findViewById<TextView>(R.id.tv_message)
        tvMessage.text = message
        if (dialog == null || dialog!!.window == null) return
        val window = dialog!!.window
        window!!.setBackgroundDrawableResource(android.R.color.transparent)
    }

    override fun dismiss() {
        try {
            super.dismiss()
        } catch (e: Exception) {
        }
    }

    fun updateMessage(message: String) {
        val tvMessage = dialog!!.findViewById<TextView>(R.id.tv_message)
        tvMessage.text = message
    }

    override fun show(manager: FragmentManager, tag: String?) {
        try {
            super.show(manager, tag)
        } catch (e: Exception) {
        }
    }

    companion object {
        private const val PROGRESS_CONTENT_SIZE_DP = 80
    }
}