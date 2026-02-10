package com.example.videojuegos.domain.usecase

import com.example.videojuegos.domain.models.Videojuego
import com.example.videojuegos.domain.repository.VideojuegoRepository

class GetVideojuegoByIdUseCase(private val repository: VideojuegoRepository) {
    suspend operator fun invoke(id: Int): Videojuego? {
        return repository.obtenerPorId(id)
    }
}
