package com.example.database

import com.example.models.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRemoteData @Inject constructor(val mongoAppClient: MongoAppClient) : UserSource {
    override fun getUser(token: String): Flow<User?> {
        return mongoAppClient.getUserFromToken(token)
    }
}

@dagger.Module
class UserRemoteDataModule {
    fun provide(
        mongoAppClient: MongoAppClient
    ): UserRemoteData {
        return UserRemoteData(mongoAppClient)
    }
}