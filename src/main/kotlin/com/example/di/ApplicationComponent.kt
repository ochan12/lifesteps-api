package com.example.di

import com.example.database.MongoAppClientModule
import com.example.database.RemoteData
import com.example.database.RemoteDataModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [MongoAppClientModule::class, RemoteDataModule::class])
interface ApplicationComponent {
    fun remoteData(): RemoteData
}