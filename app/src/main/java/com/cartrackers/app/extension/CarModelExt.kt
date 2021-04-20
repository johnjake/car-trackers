package com.cartrackers.app.extension

import android.content.Context
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.cartrackers.app.R

inline fun ImageView.toCarModel(userId: Int, context: Context) {
    when(userId) {
        1 -> this.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.user_1))
        2 -> this.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.user_2))
        3 -> this.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.user_3))
        4 -> this.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.user_4))
        5 -> this.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.user_5))
        6 -> this.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.user_6))
        7 -> this.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.user_7))
        8 -> this.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.user_8))
        9 -> this.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.user_9))
        10 -> this.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.user_10))
    }
}