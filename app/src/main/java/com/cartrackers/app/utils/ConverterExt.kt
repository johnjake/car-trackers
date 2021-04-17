package com.cartrackers.app.utils

import android.annotation.SuppressLint
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.security.Key
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec
import kotlin.reflect.KClass

@SuppressLint("GetInstance")
inline fun<reified T: Any?> String.toEncryptedString(key: String): ByteArray {
    val aesKey: Key = SecretKeySpec(key.toByteArray(), advancedEncryption)
    val cipher = Cipher.getInstance(advancedEncryption)
    cipher.init(Cipher.ENCRYPT_MODE, aesKey)
    return cipher.doFinal(this.toByteArray())
}

@SuppressLint("GetInstance")
inline fun<reified T: Any?> ByteArray.toDecryptedString(key: String): String {
    val aesKey: Key = SecretKeySpec(key.toByteArray(), advancedEncryption)
    val cipher = Cipher.getInstance(advancedEncryption)
    cipher.init(Cipher.DECRYPT_MODE, aesKey)
    return String(cipher.doFinal(this)).trim()
}

@TypeConverter
inline fun<reified T: Any?> List<T>.toStringType(): String {
    require(this.isNotEmpty()) { throwListException }
    return Gson().toJson(this)
}

@TypeConverter
inline fun<reified T: Any?> String.toListType(): List<T> {
    return Gson().fromJson(this, object : TypeToken<List<T?>?>() {}.type)
}

@TypeConverter
inline fun<reified T: Any?> T.toJsonType(): String {
    return Gson().toJson(this)
}

@TypeConverter
inline fun<reified T: Any?> String.toClassType(): T {
    return Gson().fromJson(this, T::class.java)
}

/** determined object class type using reflection **/
inline fun<reified T: Any> KClass<T>.toCheckClassType(): String {
    val generic = T::class
    return when {
        generic.isAbstract -> "Abstract"
        generic.isCompanion -> "Abstract"
        generic.isData -> "Data"
        generic.isFinal -> "Final"
        generic.isInner -> "Inner"
        generic.isSealed -> "Sealed"
        else -> "Unknown Type"
    }
}


const val throwListException = "Not a valid Array"
const val advancedEncryption = "AES"
const val encryptionKey = "#$657LgJi_45%^"
