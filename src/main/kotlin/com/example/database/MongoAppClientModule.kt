package com.example.database

import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
class MongoAppClientModule {
    @Provides
    fun provide(): MongoAppClient {
        return MongoAppClient()
    }
}