package com.example.videojuegos.di

import android.app.Application
import com.example.videojuegos.data.repository.FirebaseVideojuegoRepositoryImpl
import com.example.videojuegos.data.source.FirebaseDataSource
import com.example.videojuegos.domain.repository.VideojuegoRepository
import com.example.videojuegos.domain.usecase.AddVideojuegoUseCase
import com.example.videojuegos.domain.usecase.DeleteVideojuegoUseCase
import com.example.videojuegos.domain.usecase.GetAllVideojuegosUseCase
import com.example.videojuegos.domain.usecase.GetVideojuegoByIdUseCase
import com.example.videojuegos.domain.usecase.UpdateVideojuegoUseCase
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class FirebaseRepository

@Module
@InstallIn(SingletonComponent::class)
object FirebaseRepositoryModule {

    @Singleton
    @Provides
    fun provideFirebaseDataSource(firestore: FirebaseFirestore): FirebaseDataSource {
        return FirebaseDataSource(firestore)
    }

    @Singleton
    @Provides
    @FirebaseRepository
    fun provideFirebaseVideojuegoRepository(firebaseDataSource: FirebaseDataSource): VideojuegoRepository {
        return FirebaseVideojuegoRepositoryImpl(firebaseDataSource)
    }

    // Firebase repository is provided above. Do not provide duplicate use-case bindings here.
}
