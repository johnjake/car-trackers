package com.cartrackers.app.utils.ripple_effect

import android.content.Context
import android.graphics.Color
import android.util.DisplayMetrics
import kotlin.math.max
import kotlin.math.min
import kotlin.math.roundToInt

object RippleUtilities {
    fun mapValueFromRangeToRange(value: Double, fromLow: Double, fromHigh: Double, toLow: Double, toHigh: Double): Double {
        return toLow + (value - fromLow) / (fromHigh - fromLow) * (toHigh - toLow)
    }

    fun clamp(value: Double, low: Double, high: Double): Double {
        return min(max(value, low), high)
    }

    fun darkenColor(color: Int, multiplier: Float): Int {
        val hsv = FloatArray(3)
        Color.colorToHSV(color, hsv)
        hsv[2] *= multiplier // value component
        return Color.HSVToColor(hsv)
    }

    fun dpToPx(context: Context, dp: Int): Int {
        val displayMetrics = context.resources.displayMetrics
        return (dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT)).roundToInt()
    }
}