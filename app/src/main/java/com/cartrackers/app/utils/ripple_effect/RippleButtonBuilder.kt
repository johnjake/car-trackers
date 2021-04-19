package com.cartrackers.app.utils.ripple_effect

import android.content.Context

class RippleButtonBuilder(private val context: Context) {
    private val sparkButton: TrackRippleEffectButton = TrackRippleEffectButton(context)
    fun setActiveImage(resourceId: Int): RippleButtonBuilder {
        sparkButton.imageResourceIdActive = resourceId
        return this
    }

    fun setInactiveImage(resourceId: Int): RippleButtonBuilder {
        sparkButton.imageResourceIdInactive = resourceId
        return this
    }

    fun setPrimaryColor(color: Int): RippleButtonBuilder {
        sparkButton.primaryColor = color
        return this
    }

    fun setSecondaryColor(color: Int): RippleButtonBuilder {
        sparkButton.secondaryColor = color
        return this
    }

    fun setImageSizePx(px: Int): RippleButtonBuilder {
        sparkButton.imageSize = px
        return this
    }

    fun setImageSizeDp(dp: Int): RippleButtonBuilder {
        sparkButton.imageSize = RippleUtilities.dpToPx(context, dp)
        return this
    }

    fun setAnimationSpeed(value: Float): RippleButtonBuilder {
        sparkButton.animationSpeed = value.toDouble()
        return this
    }

    fun build(): TrackRippleEffectButton {
        sparkButton.init()
        return sparkButton
    }

}