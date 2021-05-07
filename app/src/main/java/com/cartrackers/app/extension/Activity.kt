package com.cartrackers.app.extension

import android.app.Activity
import android.content.Context
import android.graphics.Typeface
import android.text.Editable
import android.view.View
import android.widget.Toast
import com.cartrackers.app.R
import com.cartrackers.app.utils.alert_dialog.ListenerCallBack
import com.cartrackers.app.utils.alert_dialog.TrackerAlertDialog
import com.google.android.material.bottomnavigation.BottomNavigationView

fun Activity.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun Editable.toVerifyField(context: Context, title: String): Int {
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

fun Activity?.hideNavigation() {
    val bottomNavigationView = this?.findViewById<BottomNavigationView>(R.id.bottomNavigation)
    bottomNavigationView?.visibility = View.GONE
}

fun Activity?.showNavigation() {
    val bottomNavigationView = this?.findViewById<BottomNavigationView>(R.id.bottomNavigation)
    bottomNavigationView?.visibility = View.VISIBLE
}
