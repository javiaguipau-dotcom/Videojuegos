package com.example.videojuegos.data.repository

import com.example.videojuegos.data.source.LocalDataSource
import com.example.videojuegos.domain.models.Videojuego
import com.example.videojuegos.domain.repository.VideojuegoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class VideojuegoRepositoryImpl(private val localDataSource: LocalDataSource) : VideojuegoRepository {

    override suspend fun obtenerTodos(): List<Videojuego> = withContext(Dispatchers.IO) {
        localDataSource.getAll()
    }

    override suspend fun obtenerPorId(id: Int): Videojuego? = withContext(Dispatchers.IO) {
        localDataSource.getById(id)
    }

    override suspend fun agregar(videojuego: Videojuego): Boolean = withContext(Dispatchers.IO) {
        localDataSource.save(videojuego)
    }

    override suspend fun actualizar(videojuego: Videojuego): Boolean = withContext(Dispatchers.IO) {
        localDataSource.update(videojuego)
    }

    override suspend fun eliminar(id: Int): Boolean = withContext(Dispatchers.IO) {
        localDataSource.delete(id)
    }
}
