package com.cartrackers.app.extension

import android.content.Context
import android.graphics.Color
import androidx.core.content.ContextCompat
import com.cartrackers.app.R
import de.hdodenhof.circleimageview.CircleImageView

inline fun CircleImageView.toAvatar(userId: Int, context: Context) {
    this.borderColor = Color.parseColor("#FFB222")
    this.setPadding(1, 1, 1, 1)
    this.borderWidth = 5
    when(userId) {
        1 -> this.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.leanne_graham))
        2 -> this.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ervin_howell))
        3 -> this.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.clementine_bauch))
        4 -> this.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.patricia_lebsack))
        5 -> this.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.chelsey_dietrich))
        6 -> this.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.dennis_schulist))
        7 -> this.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.kurtis_weissnat))
        8 -> this.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.nicholas_runolfsdottir))
        9 -> this.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.glenna_reichert))
        10 -> this.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.clementina_dubuque))
        else -> this.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_unknow_person))
    }
}

inline fun CircleImageView.toCountry(userId: Int, context: Context) {
    this.borderColor = Color.parseColor("#75757B")
    this.setPadding(1, 1, 1, 1)
    this.borderWidth = 5
    this.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_map_item))
}