package com.example.videojuegos.domain.repository

import com.example.videojuegos.domain.models.Videojuego

interface VideojuegoRepository {
    suspend fun obtenerTodos(): List<Videojuego>
    suspend fun obtenerPorId(id: Int): Videojuego?
    suspend fun agregar(videojuego: Videojuego): Boolean
    suspend fun actualizar(videojuego: Videojuego): Boolean
    suspend fun eliminar(id: Int): Boolean
}
