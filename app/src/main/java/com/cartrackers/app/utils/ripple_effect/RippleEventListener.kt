package com.cartrackers.app.utils.ripple_effect

import android.widget.ImageView

interface RippleEventListener {
    fun onEvent(button: ImageView?, buttonState: Boolean)
}