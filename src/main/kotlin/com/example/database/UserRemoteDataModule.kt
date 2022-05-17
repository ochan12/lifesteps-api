package com.example.database

@dagger.Module
class UserRemoteDataModule {
    fun provide(
        mongoAppClient: MongoAppClient
    ): UserRemoteData {
        return UserRemoteData(mongoAppClient)
    }
}