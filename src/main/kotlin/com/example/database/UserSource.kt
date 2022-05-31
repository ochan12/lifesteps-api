package com.example.database

import com.example.models.User
import kotlinx.coroutines.flow.Flow

interface UserSource {
    fun getUser(username: String, token: String): Flow<User?>

}