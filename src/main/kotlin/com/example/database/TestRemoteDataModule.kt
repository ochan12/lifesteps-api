package com.example.database

import dagger.Module
import dagger.Provides

@Module
class TestRemoteDataModule {
    @Provides
    fun provide(): TestRemoteData {
        return TestRemoteData()
    }
}