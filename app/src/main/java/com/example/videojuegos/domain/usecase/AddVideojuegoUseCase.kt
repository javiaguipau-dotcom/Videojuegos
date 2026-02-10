package com.example.videojuegos.domain.usecase

import com.example.videojuegos.domain.models.Videojuego
import com.example.videojuegos.domain.repository.VideojuegoRepository

class AddVideojuegoUseCase(private val repository: VideojuegoRepository) {
    suspend operator fun invoke(videojuego: Videojuego): Boolean {
        return repository.agregar(videojuego)
    }
}
