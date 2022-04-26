package com.example.di

import com.example.database.*
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [MongoAppClientModule::class, RemoteDataModule::class])
interface ApplicationComponent {
    fun remoteData(): RemoteData
}

@Singleton
@Component(modules = [TestRemoteDataModule::class])
interface TestComponent {
    fun remoteData(): TestRemoteData
}