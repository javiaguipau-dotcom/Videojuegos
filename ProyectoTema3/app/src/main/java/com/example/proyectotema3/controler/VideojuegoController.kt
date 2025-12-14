package com.example.proyectotema3.controler

import com.example.proyectotema3.models.Videojuego

class VideojuegoController {

    private val lista = mutableListOf<Videojuego>()

    fun getListado() = lista

    fun insertar(v: Videojuego) {
        lista.add(v)
    }

    fun borrar(id: Int, onFinish: () -> Unit) {
        lista.removeAll { it.id == id }
        onFinish()
    }
}
