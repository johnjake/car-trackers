package com.cartrackers.app.utils.ripple_effect

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.TargetApi
import android.content.Context
import android.graphics.PorterDuff
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.view.animation.OvershootInterpolator
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.cartrackers.app.R
import de.hdodenhof.circleimageview.CircleImageView

class TrackRippleEffectButton : FrameLayout, View.OnClickListener {
    var imageResourceIdActive = INVALID_RESOURCE_ID
    var imageResourceIdInactive = INVALID_RESOURCE_ID
    var imageSize = 0
    var dotsSize = 0
    var circleSize = 0
    var secondaryColor = 0
    var primaryColor = 0
    var activeImageTint = 0
    var inActiveImageTint = 0
    var dotsView: DotsView? = null
    private lateinit var circleView: CircleView
    private lateinit var imageView: CircleImageView
    var pressOnTouch = true
    var animationSpeed = 1.0
    private var isChecked = false
    private var animatorSet: AnimatorSet? = null
    private var listener: RippleEventListener? = null

    constructor(context: Context) : super(context)

    constructor(context: Context?, attrs: AttributeSet) : super(context!!, attrs) {
        getStuffFromXML(attrs)
        init()
    }

    constructor(context: Context?, attrs: AttributeSet, defStyleAttr: Int) : super(context!!, attrs, defStyleAttr) {
        getStuffFromXML(attrs)
        init()
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context?, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(
        context!!, attrs, defStyleAttr, defStyleRes) {
        getStuffFromXML(attrs)
        init()
    }

    fun setColors(startColor: Int, endColor: Int) {
        secondaryColor = startColor
        primaryColor = endColor
    }

    fun setAnimationSpeed(animationSpeed: Float) {
        this.animationSpeed = animationSpeed.toDouble()
    }

    fun init() {
        circleSize = (imageSize * CIRCLEVIEW_SIZE_FACTOR).toInt()
        dotsSize = (imageSize * DOTVIEW_SIZE_FACTOR).toInt()
        LayoutInflater.from(context).inflate(R.layout.layout_ripple_effect, this, true)
        circleView = findViewById<View>(R.id.vCircle) as CircleView
        circleView.setColors(secondaryColor, primaryColor)
        circleView.layoutParams.height = circleSize
        circleView.layoutParams.width = circleSize
        dotsView = findViewById<View>(R.id.vDotsView) as DotsView
        dotsView!!.layoutParams.width = dotsSize
        dotsView!!.layoutParams.height = dotsSize
        dotsView!!.setColors(secondaryColor, primaryColor)
        dotsView!!.setMaxDotSize((imageSize * DOTS_SIZE_FACTOR).toInt())
        imageView = findViewById<View>(R.id.ivImage) as CircleImageView
        imageView.layoutParams.height = imageSize
        imageView.layoutParams.width = imageSize
        when {
            imageResourceIdInactive != INVALID_RESOURCE_ID -> { // should load inactive img first
                imageView.setImageResource(imageResourceIdInactive)
                imageView.setColorFilter(inActiveImageTint, PorterDuff.Mode.SRC_ATOP)
            }
            imageResourceIdActive != INVALID_RESOURCE_ID -> {
                imageView.setImageResource(imageResourceIdActive)
                imageView.setColorFilter(activeImageTint, PorterDuff.Mode.SRC_ATOP)
            }
            else -> {
                throw IllegalArgumentException("One of Inactive/Active Image Resources are required!!")
            }
        }
        setOnTouchListener()
        setOnClickListener(this)
    }

    /**
     * Call this function to start spark animation
     */
    fun playAnimation() {
        if (animatorSet != null) {
            animatorSet!!.cancel()
        }
        imageView.animate().cancel()
        imageView.scaleX = 0f
        imageView.scaleY = 0f
        circleView.setInnerCircleRadiusProgress(0F)
        circleView.setOuterCircleRadiusProgress(0F)
        dotsView!!.setCurrentProgress(0F)
        animatorSet = AnimatorSet()
        val outerCircleAnimator: ObjectAnimator = ObjectAnimator.ofFloat<CircleView>(circleView,
            CircleView.OUTER_CIRCLE_RADIUS_PROGRESS, 0.1f, 1f)
        outerCircleAnimator.duration = (250 / animationSpeed).toLong()
        outerCircleAnimator.interpolator = DECCELERATE_INTERPOLATOR
        val innerCircleAnimator: ObjectAnimator = ObjectAnimator.ofFloat<CircleView>(circleView, CircleView.INNER_CIRCLE_RADIUS_PROGRESS, 0.1f, 1f)
        innerCircleAnimator.duration = (200 / animationSpeed).toLong()
        innerCircleAnimator.startDelay = (200 / animationSpeed).toLong()
        innerCircleAnimator.interpolator = DECCELERATE_INTERPOLATOR
        val starScaleYAnimator = ObjectAnimator.ofFloat(imageView, ImageView.SCALE_Y, 0.2f, 1f)
        starScaleYAnimator.duration = (350 / animationSpeed).toLong()
        starScaleYAnimator.startDelay = (250 / animationSpeed).toLong()
        starScaleYAnimator.interpolator = OVERSHOOT_INTERPOLATOR
        val starScaleXAnimator = ObjectAnimator.ofFloat(imageView, ImageView.SCALE_X, 0.2f, 1f)
        starScaleXAnimator.duration = (350 / animationSpeed).toLong()
        starScaleXAnimator.startDelay = (250 / animationSpeed).toLong()
        starScaleXAnimator.interpolator = OVERSHOOT_INTERPOLATOR
        val dotsAnimator: ObjectAnimator = ObjectAnimator.ofFloat<DotsView>(dotsView, DotsView.DOTS_PROGRESS, 0f, 1f)
        dotsAnimator.duration = (900 / animationSpeed).toLong()
        dotsAnimator.startDelay = (50 / animationSpeed).toLong()
        dotsAnimator.interpolator = ACCELERATE_DECELERATE_INTERPOLATOR
        animatorSet!!.playTogether(
            outerCircleAnimator,
            innerCircleAnimator,
            starScaleYAnimator,
            starScaleXAnimator,
            dotsAnimator
        )
        animatorSet!!.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationCancel(animation: Animator) {
                circleView.setInnerCircleRadiusProgress(0F)
                circleView.setOuterCircleRadiusProgress(0F)
                dotsView!!.setCurrentProgress(0F)
                imageView.scaleX = 1f
                imageView.scaleY = 1f
            }
        })
        animatorSet!!.start()
    }

    /**
     * Change Button State (Works only if both active and disabled image resource is defined)
     *
     * @param flag desired checked state of the button
     */
    fun setChecked(flag: Boolean) {
        isChecked = flag
        imageView.setImageResource(if (isChecked) imageResourceIdActive else imageResourceIdInactive)
        imageView.setColorFilter(if (isChecked) activeImageTint else inActiveImageTint, PorterDuff.Mode.SRC_ATOP)
    }

    fun setEventListener(listener: RippleEventListener?) {
        this.listener = listener
    }

    fun pressOnTouch(pressOnTouch: Boolean) {
        this.pressOnTouch = pressOnTouch
        init()
    }

    override fun onClick(v: View) {
        if (imageResourceIdInactive != INVALID_RESOURCE_ID) {
            isChecked = !isChecked
            imageView.setImageResource(if (isChecked) imageResourceIdActive else imageResourceIdInactive)
            imageView.setColorFilter(if (isChecked) activeImageTint else inActiveImageTint, PorterDuff.Mode.SRC_ATOP)
            if (animatorSet != null) {
                animatorSet!!.cancel()
            }
            if (isChecked) {
                circleView.visibility = View.VISIBLE
                dotsView!!.visibility = View.VISIBLE
                playAnimation()
            } else {
                dotsView!!.visibility = View.INVISIBLE
                circleView.visibility = View.GONE
            }
        } else {
            playAnimation()
        }
        if (listener != null) {
            listener!!.onEvent(imageView, isChecked)
        }
    }

    private fun setOnTouchListener() {
        if (pressOnTouch) {
            setOnTouchListener { v, event ->
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        imageView.animate().scaleX(0.8f).scaleY(0.8f).setDuration(150).interpolator = DECCELERATE_INTERPOLATOR
                        isPressed = true
                    }
                    MotionEvent.ACTION_MOVE -> {
                        val x = event.x
                        val y = event.y
                        val isInside = x > 0 && x < width && y > 0 && y < height
                        if (isPressed != isInside) {
                            isPressed = isInside
                        }
                    }
                    MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                        imageView.animate().scaleX(1f).scaleY(1f).interpolator = DECCELERATE_INTERPOLATOR
                        if (isPressed) {
                            performClick()
                            isPressed = false
                        }
                    }
                }
                true
            }
        } else {
            setOnTouchListener(null)
        }
    }

    private fun getStuffFromXML(attr: AttributeSet) {
        val a = context.obtainStyledAttributes(attr, R.styleable.sparkbutton)
        imageSize = a.getDimensionPixelOffset(R.styleable.sparkbutton_sparkbutton_iconSize, RippleUtilities.dpToPx(context, 50))
        imageResourceIdActive = a.getResourceId(R.styleable.sparkbutton_sparkbutton_activeImage, INVALID_RESOURCE_ID)
        imageResourceIdInactive = a.getResourceId(R.styleable.sparkbutton_sparkbutton_inActiveImage, INVALID_RESOURCE_ID)
        primaryColor = ContextCompat.getColor(context, a.getResourceId(R.styleable.sparkbutton_sparkbutton_primaryColor, R.color.spark_primary_color))
        secondaryColor = ContextCompat.getColor(context, a.getResourceId(R.styleable.sparkbutton_sparkbutton_secondaryColor, R.color.spark_secondary_color))
        activeImageTint = ContextCompat.getColor(context, a.getResourceId(R.styleable.sparkbutton_sparkbutton_activeImageTint, R.color.spark_image_tint))
        inActiveImageTint = ContextCompat.getColor(context, a.getResourceId(R.styleable.sparkbutton_sparkbutton_inActiveImageTint, R.color.spark_image_tint))
        pressOnTouch = a.getBoolean(R.styleable.sparkbutton_sparkbutton_pressOnTouch, true)
        animationSpeed = a.getFloat(R.styleable.sparkbutton_sparkbutton_animationSpeed, 1f).toDouble()
        // recycle typedArray
        a.recycle()
    }

    companion object {
        private val DECCELERATE_INTERPOLATOR = DecelerateInterpolator()
        private val ACCELERATE_DECELERATE_INTERPOLATOR = AccelerateDecelerateInterpolator()
        private val OVERSHOOT_INTERPOLATOR = OvershootInterpolator(4F)
        private const val INVALID_RESOURCE_ID = -1
        private const val DOTVIEW_SIZE_FACTOR = 3f
        private const val DOTS_SIZE_FACTOR = .08f
        private const val CIRCLEVIEW_SIZE_FACTOR = 1.4f
    }
}