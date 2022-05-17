package com.example.database

import com.example.models.User
import kotlinx.coroutines.flow.Flow

interface UserSource {
    fun getUser(token: String): Flow<User?>

}