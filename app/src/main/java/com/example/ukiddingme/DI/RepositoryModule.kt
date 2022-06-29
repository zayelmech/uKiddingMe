package com.example.ukiddingme.DI

import com.example.ukiddingme.network.JokesRepository
import com.example.ukiddingme.network.JokesRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun providesJokesRepository(
        jokesRepositoryImpl: JokesRepositoryImpl
    ): JokesRepository

}