package com.example.videojuegos.interfaces

import com.example.videojuegos.models.Videojuego

interface InterfaceDao {
    fun getAll(): MutableList<Videojuego>
    fun delete(id: Int)
    fun insert(videojuego: Videojuego)
    fun update(videojuego: Videojuego)
}
