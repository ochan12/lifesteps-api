package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class Contact(
    val repository: String? = null,
    val email: String? = null,
    val phone: String? = null,
    val linkedIn: String? = null
) {
    data class Builder(
        private var repository: String? = null,
        private var email: String? = null,
        private var phone: String? = null,
        private var linkedIn: String? = null
    ) {
        fun setRepository(repository: String?) = apply {
            this.repository = repository
        }

        fun setEmail(email: String?) = apply {
            this.email = email
        }

        fun setPhone(phone: String?) = apply {
            this.phone = phone

        }

        fun setLinkedIn(linkedIn: String?) = apply{
            this.linkedIn = linkedIn
        }

        fun build(): Contact {
            return Contact(repository, email, phone, linkedIn)
        }
    }
}