package com.example.test

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.MutableLiveData


class AlertDelFrag(val msg: MutableLiveData<Boolean>) : DialogFragment() {


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle("Предупреждение")
                .setMessage(getString(R.string.alert_del_msg))
                .setPositiveButton(R.string.alert_del_pos_btn) {dialog, id ->
                    msg.value=true
                    dialog.cancel()
                }
                .setNegativeButton(getString(R.string.alert_del_neg_btn))
                {dialog, id ->
                    dialog.cancel()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

}
