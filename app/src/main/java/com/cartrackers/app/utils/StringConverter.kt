package com.cartrackers.app.utils

import android.annotation.SuppressLint
import java.security.Key
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

@SuppressLint("GetInstance")
inline fun<reified T: Any?> String.encryptText(key: String): ByteArray {
    val aesKey: Key = SecretKeySpec(key.toByteArray(), advancedEncrypted)
    val cipher = Cipher.getInstance(advancedEncrypted)
    cipher.init(Cipher.ENCRYPT_MODE, aesKey)
    return cipher.doFinal(this.toByteArray())
}

@SuppressLint("GetInstance")
inline fun<reified T: Any?> ByteArray.decryptText(key: String): String {
    val aesKey: Key = SecretKeySpec(key.toByteArray(), advancedEncrypted)
    val cipher = Cipher.getInstance(advancedEncrypted)
    cipher.init(Cipher.DECRYPT_MODE, aesKey)
    return String(cipher.doFinal(this)).trim()
}

const val advancedEncrypted = "AES"
