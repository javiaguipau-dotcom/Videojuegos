package com.example.videojuegos.models
data class Videojuego(
    val id: Int,
    val nombre: String,
    val descripcion: String,
    val imagenUrl: String,
    val puntuacion: Int // Valor de 0 a 5
)
