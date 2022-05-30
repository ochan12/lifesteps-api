package com.example.di

import com.example.database.*
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [MongoAppClientModule::class, RemoteDataModule::class, UserRemoteDataModule::class])
interface ApplicationComponent {
    fun remoteData(): RemoteData
    fun userRemoteData(): UserRemoteData
}

@Singleton
@Component(modules = [TestRemoteDataModule::class])
interface TestComponent {
    fun remoteData(): TestRemoteData
    fun userRemoteData(): UserRemoteData
}