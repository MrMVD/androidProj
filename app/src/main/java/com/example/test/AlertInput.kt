package com.example.test

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment


class AlertInput : DialogFragment() {


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle("Предупреждение")
                .setMessage(getString(R.string.alert_msg))
                .setPositiveButton(R.string.pos_btn) {
                        dialog, id ->  dialog.cancel()
                    ((parentFragment))
                }
                .setNegativeButton(getString(R.string.neg_btn))
                {dialog, id ->
                    dialog.cancel()

                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    }
