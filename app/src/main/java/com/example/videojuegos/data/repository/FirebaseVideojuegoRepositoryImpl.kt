package com.example.videojuegos.data.repository

import com.example.videojuegos.data.source.FirebaseDataSource
import com.example.videojuegos.domain.models.Videojuego
import com.example.videojuegos.domain.repository.VideojuegoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FirebaseVideojuegoRepositoryImpl(private val firebaseDataSource: FirebaseDataSource) : VideojuegoRepository {

    override suspend fun obtenerTodos(): List<Videojuego> = withContext(Dispatchers.IO) {
        firebaseDataSource.getAll()
    }

    override suspend fun obtenerPorId(id: Int): Videojuego? = withContext(Dispatchers.IO) {
        firebaseDataSource.getById(id)
    }

    override suspend fun agregar(videojuego: Videojuego): Boolean = withContext(Dispatchers.IO) {
        firebaseDataSource.save(videojuego)
    }

    override suspend fun actualizar(videojuego: Videojuego): Boolean = withContext(Dispatchers.IO) {
        firebaseDataSource.update(videojuego)
    }

    override suspend fun eliminar(id: Int): Boolean = withContext(Dispatchers.IO) {
        firebaseDataSource.delete(id)
    }
}
