package com.example.utils

import java.security.MessageDigest
import java.util.*

object Hasher {
    fun sha256(input: String, salt: String) = hashString("SHA-256", input, salt)

    private fun hashString(type: String, input: String, salt: String): String {
        val md = MessageDigest
            .getInstance(type)
        md.update(salt.toByteArray())

        val bytes = md.digest(input.toByteArray())
        val sb = StringBuilder()
        for (i in bytes.indices) {
            sb.append(Integer.toString((bytes[i].toInt() and 0xff) + 0x100, 16).substring(1))
        }
        return sb.toString()
    }
}