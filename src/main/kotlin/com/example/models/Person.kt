package com.example.models

import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id
import org.litote.kmongo.newId

@Serializable
data class Person(
    val name: String = "",
    val surname: String = "",
    val contact: Contact? = null,
    val birthDate: Long?,
    val userId: String? = "",
    @BsonId val key: Id<Person> = newId(),
) {
    data class Builder(
        private var name: String? = "",
        private var surname: String? = "",
        private var contact: Contact? = null,
        private var birthDate: Long? = 0,
        private var userId: String? = null
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

        fun setUserId(userId: String) = apply { this.userId = userId }

        fun build(): Person {
            return Person(name!!, surname!!, contact, birthDate, userId)
        }
    }
}