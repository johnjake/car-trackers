package com.cartrackers.app.extension

import android.app.Activity
import android.content.Context
import android.graphics.Typeface
import android.text.Editable
import android.widget.Toast
import com.cartrackers.app.utils.alert_dialog.ListenerCallBack
import com.cartrackers.app.utils.alert_dialog.TrackerAlertDialog

fun Activity.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

inline fun Editable.toVerifyField(context: Context, title: String): Int {
    if(this.isEmpty()) {
        val alertDialog = TrackerAlertDialog()
        alertDialog.alertInitialize(
            context,
            title,
            "Please fill out the $title field",
            Typeface.SANS_SERIF,
            Typeface.DEFAULT_BOLD,
            isCancelable = true,
            isNegativeBtnHide = true)
        alertDialog.setPositive("Ok", object : ListenerCallBack {
            override fun onClick(dialog: TrackerAlertDialog) {
                dialog.dismiss()
            }
        })
        alertDialog.setNegative("Ok", object : ListenerCallBack {
            override fun onClick(dialog: TrackerAlertDialog) {
                dialog.dismiss()
            }
        })
        alertDialog.show()
        return 0
    } else return 1
}