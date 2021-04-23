package com.cartrackers.app.utils.ripple_effect

import android.animation.ArgbEvaluator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Property
import android.view.View
import kotlin.math.cos
import kotlin.math.sin

class DotsView : View {
    private var color1 = -0x3ef9
    private var color2 = -0x6800
    private var color3 = -0xa8de
    private var color4 = -0xbbcca
    private val circlePaints = arrayOfNulls<Paint>(4)
    private var centerX = 0
    private var centerY = 0
    private var maxOuterDotsRadius = 0.0
    private var maxInnerDotsRadius = 0.0
    private var maxDotSize = 0.0
    private var currentProgress = 0.0
    private var currentRadius1 = 0.0
    private var currentDotSize1 = 0.0
    private var currentDotSize2 = 0.0
    private var currentRadius2 = 0.0
    private val argbEvaluator = ArgbEvaluator()

    constructor(context: Context?) : super(context) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        maxDotSize = RippleUtilities.dpToPx(context, 4).toDouble()
        for (i in circlePaints.indices) {
            circlePaints[i] = Paint()
            circlePaints[i]!!.style = Paint.Style.FILL
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        centerX = w / 2
        centerY = h / 2
        maxOuterDotsRadius = w / 2 - maxDotSize * 1
        maxInnerDotsRadius = 0.8f * maxOuterDotsRadius
    }

    override fun onDraw(canvas: Canvas) {
        drawOuterDotsFrame(canvas)
        drawInnerDotsFrame(canvas)
    }

    fun setMaxDotSize(pxUnits: Int) {
        maxDotSize = pxUnits.toDouble()
    }

    private fun drawOuterDotsFrame(canvas: Canvas) {
        for (i in 0 until DOTS_COUNT) {
            val cX = (centerX + currentRadius1 * cos(i * OUTER_DOTS_POSITION_ANGLE * Math.PI / 180)).toInt()
            val cY = (centerY + currentRadius1 * sin(i * OUTER_DOTS_POSITION_ANGLE * Math.PI / 180)).toInt()
            circlePaints[i % circlePaints.size]?.let {
                canvas.drawCircle(cX.toFloat(), cY.toFloat(), currentDotSize1.toFloat(),
                    it
                )
            }
        }
    }

    private fun drawInnerDotsFrame(canvas: Canvas) {
        for (i in 0 until DOTS_COUNT) {
            val cX = (centerX + currentRadius2 * cos((i * OUTER_DOTS_POSITION_ANGLE - 10) * Math.PI / 180)).toInt()
            val cY = (centerY + currentRadius2 * sin((i * OUTER_DOTS_POSITION_ANGLE - 10) * Math.PI / 180)).toInt()
            circlePaints[(i + 1) % circlePaints.size]?.let {
                canvas.drawCircle(cX.toFloat(), cY.toFloat(), currentDotSize2.toFloat(),
                    it
                )
            }
        }
    }

    fun setCurrentProgress(currentProgress: Float) {
        this.currentProgress = currentProgress.toDouble()
        updateInnerDotsPosition()
        updateOuterDotsPosition()
        updateDotsPaints()
        updateDotsAlpha()
        postInvalidate()
    }

    fun getCurrentProgress(): Double {
        return currentProgress
    }

    private fun updateInnerDotsPosition() {
        currentRadius2 = if (currentProgress < 0.3f) {
            RippleUtilities.mapValueFromRangeToRange(currentProgress, 0.0, 0.3, 0.0, maxInnerDotsRadius)
        } else {
            maxInnerDotsRadius
        }
        currentDotSize2 = when {
            currentProgress < 0.2 -> {
                maxDotSize
            }
            currentProgress < 0.5 -> {
                RippleUtilities.mapValueFromRangeToRange(currentProgress, 0.2, 0.5, maxDotSize, 0.3 * maxDotSize)
            }
            else -> {
                RippleUtilities.mapValueFromRangeToRange(currentProgress, 0.5, 1.0, maxDotSize * 0.3f, 0.0)
            }
        }
    }

    private fun updateOuterDotsPosition() {
        currentRadius1 = if (currentProgress < 0.3f) {
            RippleUtilities.mapValueFromRangeToRange(currentProgress, 0.0, 0.3, 0.0, maxOuterDotsRadius * 0.8f)
        } else {
            RippleUtilities.mapValueFromRangeToRange(currentProgress, 0.3, 1.0, 0.8f * maxOuterDotsRadius, maxOuterDotsRadius)
        }
        currentDotSize1 = if (currentProgress < 0.7) {
            maxDotSize
        } else {
            RippleUtilities.mapValueFromRangeToRange(currentProgress, 0.7, 1.0, maxDotSize, 0.0)
        }
    }

    private fun updateDotsPaints() {
        if (currentProgress < 0.5f) {
            val progress = RippleUtilities.mapValueFromRangeToRange(currentProgress, 0.0, 0.5, 0.0, 1.0)
            circlePaints[0]!!.color = (argbEvaluator.evaluate(progress.toFloat(), color1, color2) as Int)
            circlePaints[1]!!.color = (argbEvaluator.evaluate(progress.toFloat(), color2, color3) as Int)
            circlePaints[2]!!.color = (argbEvaluator.evaluate(progress.toFloat(), color3, color4) as Int)
            circlePaints[3]!!.color = (argbEvaluator.evaluate(progress.toFloat(), color4, color1) as Int)
        } else {
            val progress = RippleUtilities.mapValueFromRangeToRange(currentProgress, 0.5, 1.0, 0.0, 1.0)
            circlePaints[0]!!.color = (argbEvaluator.evaluate(progress.toFloat(), color2, color3) as Int)
            circlePaints[1]!!.color = (argbEvaluator.evaluate(progress.toFloat(), color3, color4) as Int)
            circlePaints[2]!!.color = (argbEvaluator.evaluate(progress.toFloat(), color4, color1) as Int)
            circlePaints[3]!!.color = (argbEvaluator.evaluate(progress.toFloat(), color1, color2) as Int)
        }
    }

    private fun updateDotsAlpha() {
        val progress = RippleUtilities.clamp(currentProgress, 0.6, 1.0)
        val alpha = RippleUtilities.mapValueFromRangeToRange(progress, 0.6, 1.0, 255.0, 0.0)
        circlePaints[0]!!.alpha = alpha.toInt()
        circlePaints[1]!!.alpha = alpha.toInt()
        circlePaints[2]!!.alpha = alpha.toInt()
        circlePaints[3]!!.alpha = alpha.toInt()
    }

    fun setColors(startColor: Int, endColor: Int) {
        color1 = startColor
        color2 = RippleUtilities.darkenColor(color1, 1.1f)
        color4 = endColor
        color3 = RippleUtilities.darkenColor(color4, 1.1f)
    }

    companion object {
        private const val DOTS_COUNT = 7
        private const val OUTER_DOTS_POSITION_ANGLE = 360 / DOTS_COUNT
        val DOTS_PROGRESS: Property<DotsView, Float> = object : Property<DotsView, Float>(Float::class.java, "dotsProgress") {
            override fun get(`object`: DotsView): Float {
                return `object`.getCurrentProgress().toFloat()
            }

            override fun set(`object`: DotsView, value: Float) {
                `object`.setCurrentProgress(value)
            }
        }
    }
}