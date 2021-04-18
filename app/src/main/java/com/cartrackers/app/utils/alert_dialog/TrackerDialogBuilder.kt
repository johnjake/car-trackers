package com.cartrackers.app.utils.alert_dialog

import android.content.Context
import android.graphics.Typeface

class TrackerDialogBuilder(private var context: Context) {
    private var titleTf: Typeface? = null
    private var subTitleTf: Typeface? = null
    private var isCancelable = false
    private lateinit var title: String
    private lateinit var subtitle: String
    private lateinit var okButtonLable: String
    private lateinit var cancelButtonLable: String
    private lateinit var okListener: ListenerCallBack
    private lateinit var cancelListener: ListenerCallBack
    private var isNegativeBtnHide = false

    fun setTitle(title: String): TrackerDialogBuilder {
        this.title = title
        return this
    }

    fun setSubTitle(subTitle: String): TrackerDialogBuilder {
        this.subtitle = subTitle
        return this
    }

    fun setTitleFont(titleFont: Typeface): TrackerDialogBuilder {
        titleTf = titleFont
        return this
    }

    fun setSubTitleFont(subTFont: Typeface): TrackerDialogBuilder {
        this.subTitleTf = subTFont
        return this
    }

    fun setPositiveButton(lable: String, listener: ListenerCallBack): TrackerDialogBuilder {
        okListener = listener
        this.okButtonLable = lable
        return this
    }

    fun setNegativeButton(label: String, listener: ListenerCallBack): TrackerDialogBuilder {
        cancelListener = listener
        this.cancelButtonLable = label
        return this
    }

    fun setCancellable(isCancelable: Boolean): TrackerDialogBuilder {
        this.isCancelable = isCancelable
        return this
    }

    fun setNegativeButtonHide(isHide: Boolean): TrackerDialogBuilder {
        isNegativeBtnHide = isHide
        return this
    }

    fun build(): TrackerAlertDialog {
        val dialog = TrackerAlertDialog()
        dialog.alertInitialize(context, title, subtitle, titleTf, subTitleTf, isCancelable, isNegativeBtnHide)
        cancelButtonLable.let { dialog.setNegative(it, cancelListener) }
        okButtonLable.let { dialog.setPositive(it, okListener) }
        return dialog
    }
}