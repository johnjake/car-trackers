package com.cartrackers.app.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.widget.Toast
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import timber.log.Timber
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
inline fun<reified T: Any?> T.toJsonType(): String? {
    return Gson().toJson(this) ?: ""
}

@TypeConverter
inline fun<reified T: Any?> String.toClassType(): T? {
    return Gson().fromJson(this, T::class.java) ?: null
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

fun Activity.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun isOnline(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val capabilities =
        connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
    if (capabilities != null) {
        when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                Timber.d( "NetworkCapabilities.TRANSPORT_CELLULAR")
                return true
            }
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                Timber.d( "NetworkCapabilities.TRANSPORT_WIFI")
                return true
            }
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                Timber.d( "NetworkCapabilities.TRANSPORT_ETHERNET")
                return true
            }
        }
    }
    return false
}


const val throwListException = "Not a valid Array"
const val advancedEncryption = "AES"
const val encryptionKey = "#$657LgJi_45%^"
const val net_connectivity = "car.tracker.net"
const val shared_room = "car.tracker.room"
const val shared_user_no = "car.tracker.user.no"
const val shared_pref = "car_track"
const val shared_counter = "car_track_counter"
