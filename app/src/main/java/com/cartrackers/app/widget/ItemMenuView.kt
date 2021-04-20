package com.cartrackers.app.widget

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.cartrackers.app.R

class ItemMenuView(context: Context, attributeSet: AttributeSet) :
    FrameLayout(context, attributeSet) {

    private val selectableItemBackground: Int
    private var _itemName: String = ""
    private var _itemDrawable: Int = -1
    private var _itemBackgroundColor: Int = -1
    private var _textColor: Int = -1

    init {
        val outValue = TypedValue()

        context.theme.resolveAttribute(
            android.R.attr.selectableItemBackground,
            outValue, true
        )

        selectableItemBackground = outValue.resourceId

        val array = context.obtainStyledAttributes(attributeSet, R.styleable.ItemMenuView)
        try {
            array.getString(R.styleable.ItemMenuView_item_text).let {
                _itemName = it.orEmpty()
            }
            array.getResourceId(R.styleable.ItemMenuView_item_drawable, 0).let {
                _itemDrawable = if (it <= 0) R.drawable.ic_arrow_right else it
            }

            array.getColor(R.styleable.ItemMenuView_item_background_color, Color.GREEN).let {
                _itemBackgroundColor = it
            }

            array.getColor(R.styleable.ItemMenuView_item_text_color, Color.GREEN).let {
                _textColor = it
            }
        } finally {
            array.recycle()
        }

        addView(ViewHolder(context, this)
            .apply {
                setItemName(_itemName)
                setImageSrc(_itemDrawable)
                setBackgroundColor(_itemBackgroundColor)
                setItemColor(_textColor)
            }
            .root)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
    }
}

private class ViewHolder(context: Context, viewGroup: ViewGroup) {
    val root: View = LayoutInflater.from(context)
        .inflate(R.layout.item_profile_menu, viewGroup, false)

    val itemName: TextView = root.findViewById(R.id.itemName)
    val image: ImageView = root.findViewById(R.id.image)

    fun setItemName(itemName: String) {
        this.itemName.text = itemName
    }

    fun setImageSrc(image: Int) {
        this.image.setImageResource(image)
    }

    fun setItemColor(color: Int) {
        this.itemName.setTextColor(color)
    }
}