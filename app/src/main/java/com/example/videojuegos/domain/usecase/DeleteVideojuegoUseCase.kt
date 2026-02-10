package com.example.videojuegos.domain.usecase

import com.example.videojuegos.domain.repository.VideojuegoRepository

class DeleteVideojuegoUseCase(private val repository: VideojuegoRepository) {
    suspend operator fun invoke(id: Int): Boolean {
        return repository.eliminar(id)
    }
}
