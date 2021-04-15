package com.cartrackers.app.security

import org.junit.jupiter.api.*
import org.testcontainers.junit.jupiter.Testcontainers
import javax.crypto.*
import javax.crypto.spec.SecretKeySpec
import com.cartrackers.app.utils.advancedEncrypted
import com.cartrackers.app.utils.decryptText
import com.cartrackers.app.utils.encryptText
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeGreaterThan
import java.security.Key

@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class EncryptionUnitTest {

    private lateinit var encryptedText: ByteArray
    private val key = "Bar12345Bar12345" // 128 bit key
    private val plainText = "Hello World"
    @Test
    @Order(1)
    fun `encryption simple string`() {
        val aesKey: Key = SecretKeySpec(key.toByteArray(), advancedEncrypted)
        val cipher = Cipher.getInstance(advancedEncrypted)
        cipher.init(Cipher.ENCRYPT_MODE, aesKey)
        val encrypted = cipher.doFinal(plainText.toByteArray())
        encryptedText = encrypted
        encrypted.size shouldBeGreaterThan 0
    }

    @Test
    @Order(2)
    fun `decrypt bytearray to plain text`() {
        val aesKey: Key = SecretKeySpec(key.toByteArray(), advancedEncrypted)
        val cipher = Cipher.getInstance(advancedEncrypted)
        cipher.init(Cipher.DECRYPT_MODE, aesKey)
        val decrypted = String(cipher.doFinal(encryptedText)).trim()
        plainText shouldBeEqualTo decrypted
        println(decrypted)
    }

    @Test
    @Order(3)
    fun `string extension for encryption`() {
      val encryptedText =  plainText.encryptText<String>(key)
      encryptedText.size shouldBeGreaterThan 0
    }

    @Test
    @Order(4)
    fun `string extension for decryption`() {
       val decryptedText = encryptedText.decryptText<ByteArray>(key)
        plainText shouldBeEqualTo decryptedText
        println(decryptedText)
    }
}



