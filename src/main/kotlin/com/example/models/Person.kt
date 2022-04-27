package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class Person(
    val name: String = "",
    val surname: String = "",
    val contact: Contact? = null,
    val birthDate: Long?
) {
    data class Builder(
        private var name: String? = "",
        private var surname: String? = "",
        private var contact: Contact? = null,
        private var birthDate: Long? = 0
    ) {
        fun setName(name: String) = apply {
            this.name = name
        }

        fun setSurname(surname: String) = apply {
            this.surname = surname
        }

        fun setContact(contact: Contact) = apply {
            this.contact = contact
        }

        fun setBirthDate(birthDate: Long) = apply {
            this.birthDate = birthDate
        }

        fun build(): Person {
            return Person(name!!, surname!!, contact, birthDate)
        }
    }
}