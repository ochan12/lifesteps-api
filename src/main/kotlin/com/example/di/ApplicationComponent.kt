package com.example.di

import com.example.database.MongoAppClientModule
import com.example.database.RemoteData
import com.example.database.RemoteDataModule
import com.example.database.TestRemoteDataModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [MongoAppClientModule::class, RemoteDataModule::class])
interface ApplicationComponent {
    fun remoteData(): RemoteData
}

@Singleton
@Component(modules = [TestRemoteDataModule::class])
interface TestComponent: ApplicationComponent {
    override fun remoteData(): RemoteData
}