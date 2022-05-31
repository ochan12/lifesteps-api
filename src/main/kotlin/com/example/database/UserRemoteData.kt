package com.example.database

import com.example.models.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import dagger.Module
import dagger.Provides

class UserRemoteData @Inject constructor(val mongoAppClient: MongoAppClient) : UserSource {
    override fun getUser(username: String, password: String): Flow<User?> {
        return mongoAppClient.getUserFromToken(username, password)
    }
}

@Module
class UserRemoteDataModule {
    @Provides
    fun provide(
        mongoAppClient: MongoAppClient
    ): UserRemoteData {
        return UserRemoteData(mongoAppClient)
    }
}