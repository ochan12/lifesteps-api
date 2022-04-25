package com.example.database

import dagger.Module
import dagger.Provides

@Module
class RemoteDataModule {
    @Provides
    fun provide(
        mongoAppClient: MongoAppClient
    ): RemoteData {
        return RemoteData(mongoAppClient)
    }
}