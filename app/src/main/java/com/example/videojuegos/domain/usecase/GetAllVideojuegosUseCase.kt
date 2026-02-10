package com.example.videojuegos.domain.usecase

import com.example.videojuegos.domain.models.Videojuego
import com.example.videojuegos.domain.repository.VideojuegoRepository

class GetAllVideojuegosUseCase(private val repository: VideojuegoRepository) {
    suspend operator fun invoke(): List<Videojuego> {
        return repository.obtenerTodos()
    }
}
