package com.example.ukiddingme.views

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class DialogMessage(
    private val message: String? =null
) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        AlertDialog.Builder(requireContext())
            .setMessage(message)
            .setNegativeButton("OK"){ _, _->
                //this.dismiss()
            }
            .create()

    companion object {
        const val TAG = "JUST_DIALOG"
    }
}
