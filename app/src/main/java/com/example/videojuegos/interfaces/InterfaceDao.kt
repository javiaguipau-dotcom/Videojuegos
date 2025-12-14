package com.example.videojuegos.interfaces

import com.example.videojuegos.models.Videojuego

interface InterfaceDao {
    fun getAll(): List<Videojuego>
    fun delete(id: Int)
    fun insertar(videojuego: Videojuego)
    // ⬅️ AÑADIDO: Método para buscar y actualizar
    fun update(videojuego: Videojuego)
    fun getById(id: Int): Videojuego? // ⬅️ AÑADIDO: Método para buscar el juego a editar
}