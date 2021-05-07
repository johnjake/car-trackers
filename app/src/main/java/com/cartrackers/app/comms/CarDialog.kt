package com.cartrackers.app.comms

import android.content.Context
import android.graphics.Typeface
import com.cartrackers.app.utils.alert_dialog.ListenerCallBack
import com.cartrackers.app.utils.alert_dialog.TrackerAlertDialog

class CarDialog {
    companion object {
        fun build(context: Context, title: String, messages: String) {
            val alertDialog = TrackerAlertDialog()
            alertDialog.alertInitialize(
                context,
                title,
                messages,
                Typeface.SANS_SERIF,
                Typeface.DEFAULT_BOLD,
                isCancelable = true,
                isNegativeBtnHide = false)
            alertDialog.setPositive("YES", object : ListenerCallBack {
                override fun onClick(dialog: TrackerAlertDialog) {
                    dialog.dismiss()
                }
            })
            alertDialog.setNegative("NO", object : ListenerCallBack {
                override fun onClick(dialog: TrackerAlertDialog) {
                    dialog.dismiss()
                }
            })
            alertDialog.show()
        }

        fun builderAlert(context: Context, title: String, messages: String) {
            val alertDialog = TrackerAlertDialog()
            alertDialog.alertInitialize(
                context,
                title,
                messages,
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
        }
    }
}
