package com.cartrackers.baseplate_persistence.utils

import androidx.room.TypeConverter
import com.google.gson.Gson

@TypeConverter
inline fun<reified T: Any?> T.toJsonType(): String? {
    return Gson().toJson(this) ?: ""
}

@TypeConverter
inline fun<reified T: Any?> String.toClassType(): T? {
    return Gson().fromJson(this, T::class.java) ?: null
}