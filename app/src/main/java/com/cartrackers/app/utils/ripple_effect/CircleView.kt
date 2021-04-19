package com.cartrackers.app.utils.ripple_effect

import android.animation.ArgbEvaluator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Property
import android.view.View

class CircleView : View {
    private var startColor = -0xa8de
    private var endColor = -0x3ef9
    private val argbEvaluator = ArgbEvaluator()
    private val circlePaint = Paint()
    private val maskPaint = Paint()
    //TODO: verify if tempBitmap is properly initiated
    private lateinit var tempBitmap: Bitmap
    private var tempCanvas: Canvas? = null
    private var outerCircleRadiusProgress = 0f
    private var innerCircleRadiusProgress = 0f
    private var maxCircleSize = 0

    constructor(context: Context?) : super(context) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        init()
    }

    private fun init() {
        circlePaint.style = Paint.Style.FILL
        maskPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        maxCircleSize = w / 2
        tempBitmap = Bitmap.createBitmap(width, width, Bitmap.Config.ARGB_8888)
        tempCanvas = Canvas(tempBitmap)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        tempCanvas!!.drawColor(0xffffff, PorterDuff.Mode.CLEAR)
        tempCanvas!!.drawCircle(width / 2.toFloat(), height / 2.toFloat(), outerCircleRadiusProgress * maxCircleSize, circlePaint)
        tempCanvas!!.drawCircle(width / 2.toFloat(), height / 2.toFloat(), innerCircleRadiusProgress * maxCircleSize, maskPaint)
        tempBitmap?.let { canvas.drawBitmap(it, 0f, 0f, null) }
    }

    fun setColors(startColor: Int, endColor: Int) {
        this.startColor = startColor
        this.endColor = endColor
    }

    fun setInnerCircleRadiusProgress(innerCircleRadiusProgress: Float) {
        this.innerCircleRadiusProgress = innerCircleRadiusProgress
        postInvalidate()
    }

    fun getInnerCircleRadiusProgress(): Float {
        return innerCircleRadiusProgress
    }

    fun setOuterCircleRadiusProgress(outerCircleRadiusProgress: Float) {
        this.outerCircleRadiusProgress = outerCircleRadiusProgress
        updateCircleColor()
        postInvalidate()
    }

    private fun updateCircleColor() {
        var colorProgress = RippleUtilities.clamp(outerCircleRadiusProgress.toDouble(), 0.5, 1.0)
        colorProgress = RippleUtilities.mapValueFromRangeToRange(colorProgress, 0.5, 1.0, 0.0, 1.0).toFloat().toDouble()
        circlePaint.color = (argbEvaluator.evaluate(colorProgress.toFloat(), startColor, endColor) as Int)
    }

    fun getOuterCircleRadiusProgress(): Float {
        return outerCircleRadiusProgress
    }

    companion object {
        val INNER_CIRCLE_RADIUS_PROGRESS: Property<CircleView, Float> = object : Property<CircleView, Float>(Float::class.java, "innerCircleRadiusProgress") {
            override fun get(`object`: CircleView): Float {
                return `object`.getInnerCircleRadiusProgress()
            }

            override fun set(`object`: CircleView, value: Float) {
                `object`.setInnerCircleRadiusProgress(value)
            }
        }
        val OUTER_CIRCLE_RADIUS_PROGRESS: Property<CircleView, Float> = object : Property<CircleView, Float>(Float::class.java, "outerCircleRadiusProgress") {
            override fun get(`object`: CircleView): Float {
                return `object`.getOuterCircleRadiusProgress()
            }

            override fun set(`object`: CircleView, value: Float) {
                `object`.setOuterCircleRadiusProgress(value)
            }
        }
    }
}