package com.naturaz.bd.core.security

import android.util.Base64
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import java.security.KeyStore
import java.nio.ByteBuffer
import java.security.GeneralSecurityException
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenCryptoManager @Inject constructor(
    @ApplicationContext context: Context
) {
    private val keyAlias = "${context.packageName}.naturaz_token_key"
    private val keySize = 256
    private val gcmTagLength = 128
    private val ivLength = 12

    private val keyStore: KeyStore = KeyStore.getInstance("AndroidKeyStore").apply { load(null) }

    private val secretKey: SecretKey by lazy {
        try {
            (keyStore.getEntry(keyAlias, null) as? KeyStore.SecretKeyEntry)?.secretKey ?: generateKey()
        } catch (ex: Exception) {
            throw IllegalStateException("Unable to load encryption key", ex)
        }
    }

    private fun generateKey(): SecretKey {
        return KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore").apply {
            init(
                KeyGenParameterSpec.Builder(
                    keyAlias,
                    KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
                )
                    .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                    .setKeySize(keySize)
                    .build()
            )
        }.generateKey()
    }

    fun encrypt(plainText: String): String {
        val cipher = Cipher.getInstance("AES/GCM/NoPadding")
        cipher.init(Cipher.ENCRYPT_MODE, secretKey)
        val iv = cipher.iv ?: throw GeneralSecurityException("No IV generated")
        val cipherText = cipher.doFinal(plainText.toByteArray(Charsets.UTF_8))
        val buffer = ByteBuffer.allocate(iv.size + cipherText.size)
        buffer.put(iv)
        buffer.put(cipherText)
        return Base64.encodeToString(buffer.array(), Base64.NO_WRAP)
    }

    fun decrypt(encrypted: String?): String? {
        if (encrypted.isNullOrEmpty()) return null
        val decoded = Base64.decode(encrypted, Base64.NO_WRAP)
        val buffer = ByteBuffer.wrap(decoded)
        val iv = ByteArray(ivLength)
        buffer.get(iv)
        val cipherText = ByteArray(buffer.remaining())
        buffer.get(cipherText)
        val cipher = Cipher.getInstance("AES/GCM/NoPadding")
        cipher.init(Cipher.DECRYPT_MODE, secretKey, GCMParameterSpec(gcmTagLength, iv))
        val plainBytes = cipher.doFinal(cipherText)
        return plainBytes.toString(Charsets.UTF_8)
    }
}
