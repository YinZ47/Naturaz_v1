package com.naturaz.bd.core.security

import android.content.Context
import android.util.Base64
import androidx.security.crypto.MasterKey
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
    context: Context
) {
    private val keyAlias = "naturaz_token_key"
    private val keySize = 256
    private val gcmTagLength = 128
    private val ivLength = 12

    private val masterKey: MasterKey = MasterKey.Builder(context, keyAlias)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val secretKey: SecretKey by lazy {
        try {
            val keyStore = java.security.KeyStore.getInstance("AndroidKeyStore").apply { load(null) }
            (keyStore.getEntry(masterKey.alias, null) as? java.security.KeyStore.SecretKeyEntry)?.secretKey
                ?: generateKey()
        } catch (ex: Exception) {
            throw IllegalStateException("Unable to load encryption key", ex)
        }
    }

    private fun generateKey(): SecretKey {
        return KeyGenerator.getInstance("AES", "AndroidKeyStore").apply {
            init(android.security.keystore.KeyGenParameterSpec.Builder(
                masterKey.alias,
                android.security.keystore.KeyProperties.PURPOSE_ENCRYPT or android.security.keystore.KeyProperties.PURPOSE_DECRYPT
            ).setBlockModes(android.security.keystore.KeyProperties.BLOCK_MODE_GCM)
                .setEncryptionPaddings(android.security.keystore.KeyProperties.ENCRYPTION_PADDING_NONE)
                .setKeySize(keySize)
                .build())
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
