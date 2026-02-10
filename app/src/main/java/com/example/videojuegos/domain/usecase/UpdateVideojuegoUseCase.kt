package com.example.videojuegos.domain.usecase

import com.example.videojuegos.domain.models.Videojuego
import com.example.videojuegos.domain.repository.VideojuegoRepository

class UpdateVideojuegoUseCase(private val repository: VideojuegoRepository) {
    suspend operator fun invoke(videojuego: Videojuego): Boolean {
        return repository.actualizar(videojuego)
    }
}
