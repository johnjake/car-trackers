package com.cartrackers.app.extension

import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.ScaleAnimation

fun View.itemAnimation(fadeDuration: Long) {
    val anim = AlphaAnimation(0.0f, 1.0f)
    anim.duration = fadeDuration
    this.startAnimation(anim)
}

private fun View.itemScaleAnimation(fadeDuration: Long) {
    val anim = ScaleAnimation(
        0.0f,
        1.0f,
        0.0f,
        1.0f,
        Animation.RELATIVE_TO_SELF,
        0.5f,
        Animation.RELATIVE_TO_SELF,
        0.5f
    )
    anim.duration = fadeDuration
    this.startAnimation(anim)
}
