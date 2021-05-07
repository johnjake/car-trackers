package com.cartrackers.app.comms

import android.content.Context
import android.graphics.Typeface
import com.cartrackers.app.utils.alert_dialog.ListenerCallBack
import com.cartrackers.app.utils.alert_dialog.TrackerAlertDialog

class PopupDialog(
    private val listener: (confirm: Boolean) -> Unit
) {
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
                listener(true)
                dialog.dismiss()
            }
        })
        alertDialog.setNegative("NO", object : ListenerCallBack {
            override fun onClick(dialog: TrackerAlertDialog) {
                listener(false)
                dialog.dismiss()
            }
        })
        alertDialog.show()
    }
}
