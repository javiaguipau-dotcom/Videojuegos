package com.example.videojuegos.di

import android.app.Application
import com.example.videojuegos.data.repository.VideojuegoRepositoryImpl
import com.example.videojuegos.data.source.LocalDataSource
import com.example.videojuegos.domain.repository.VideojuegoRepository
import com.example.videojuegos.domain.usecase.AddVideojuegoUseCase
import com.example.videojuegos.domain.usecase.DeleteVideojuegoUseCase
import com.example.videojuegos.domain.usecase.GetAllVideojuegosUseCase
import com.example.videojuegos.domain.usecase.GetVideojuegoByIdUseCase
import com.example.videojuegos.domain.usecase.UpdateVideojuegoUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object VideojuegoModule {

    @Singleton
    @Provides
    fun provideLocalDataSource(application: Application): LocalDataSource {
        return LocalDataSource(application)
    }

    @Singleton
    @Provides
    fun provideVideojuegoRepository(localDataSource: LocalDataSource): VideojuegoRepository {
        return VideojuegoRepositoryImpl(localDataSource)
    }

    @Provides
    @Singleton
    fun provideGetAllVideojuegosUseCase(repository: VideojuegoRepository): GetAllVideojuegosUseCase {
        return GetAllVideojuegosUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetVideojuegoByIdUseCase(repository: VideojuegoRepository): GetVideojuegoByIdUseCase {
        return GetVideojuegoByIdUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideAddVideojuegoUseCase(repository: VideojuegoRepository): AddVideojuegoUseCase {
        return AddVideojuegoUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideUpdateVideojuegoUseCase(repository: VideojuegoRepository): UpdateVideojuegoUseCase {
        return UpdateVideojuegoUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideDeleteVideojuegoUseCase(repository: VideojuegoRepository): DeleteVideojuegoUseCase {
        return DeleteVideojuegoUseCase(repository)
    }
}
