package com.cartrackers.app.utils.alert_dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.cartrackers.app.R

class TrackerAlertDialog {
    private lateinit var dialog: Dialog
    private lateinit var titleTV: TextView
    private lateinit var subTitleTv: TextView
    private lateinit var bntSubmit: Button
    private lateinit var bntCancel: Button
    // private View separator;
    private lateinit var okButtonClickListener: ListenerCallBack
    private lateinit var cancelButtonClickListener: ListenerCallBack
    private var isNegativeBtnHide = false

    fun alertInitialize(context: Context, title: String, messages: String, titleFont: Typeface?, subtitleFont: Typeface?, isCancelable: Boolean, isNegativeBtnHide: Boolean) {

        this.isNegativeBtnHide = isNegativeBtnHide

        dialog = Dialog(context)
        dialog.setContentView(R.layout.tracker_alert_layout)
        dialog.setCancelable(isCancelable)

        if (dialog.window != null) {
            dialog.run { window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT)) }
        }

        initDialogViw()
        setTitle(title)
        setMessages(messages)
        setSubTitleFont(subtitleFont)
        setTitleFont(titleFont)
        initClickEvents()
    }

    fun setPositive(okLabel: String, listener: ListenerCallBack) {
        okButtonClickListener = listener
        dismiss()
        setPositiveLabel(okLabel)
    }

    fun setNegative(cancelLabel: String, listener: ListenerCallBack?) {
        if (listener != null) {
            cancelButtonClickListener = listener
            dismiss()
            setNegativeLabel(cancelLabel)
        }
    }

    fun show() {
        if (isNegativeBtnHide) {
            bntCancel.visibility = View.GONE
            // separator.setVisibility(View.GONE);
        }
        dialog.show()
    }

    private fun setTitle(title: String?) {
        titleTV.text = title
    }

    private fun setMessages(subtitle: String?) {
        subTitleTv.text = subtitle
    }

    private fun setPositiveLabel(positive: String) {
        bntSubmit.text = positive
    }

    private fun setNegativeLabel(negative: String) {
        bntCancel.text = negative
    }

    private fun setSubTitleFont(appleFont: Typeface?) {
        if (appleFont != null) {
            // title_lbl.setTypeface(appleFont);
            subTitleTv.typeface = appleFont
            // tvOK!!.typeface = appleFont
            // tvCancel!!.typeface = appleFont
        }
    }

    private fun setTitleFont(appleFont: Typeface?) {
        if (appleFont != null) {
            titleTV.typeface = appleFont
        }
    }

    fun dismiss() {
        dialog.dismiss()
    }

    // positive and negative button initialise here
    private fun initClickEvents() {
        bntSubmit.setOnClickListener {
            if (okButtonClickListener != null) {
                okButtonClickListener.onClick(this@TrackerAlertDialog)
            }
        }
        bntCancel.setOnClickListener {
            if (cancelButtonClickListener != null) {
                cancelButtonClickListener.onClick(this@TrackerAlertDialog)
            }
        }
    }

    // init all view here
    private fun initDialogViw() {
        titleTV = dialog.findViewById(R.id.tv1)
        subTitleTv = dialog.findViewById(R.id.tv2)
        bntSubmit = dialog.findViewById(R.id.bntSubmit)
        bntCancel = dialog.findViewById(R.id.bntCancel)
        // separator = dialog.findViewById(R.id.separatorView);
        // tvOK = dialog!!.findViewById(R.id.tvok)
        // tvCancel = dialog!!.findViewById(R.id.tvCan)
    }
}