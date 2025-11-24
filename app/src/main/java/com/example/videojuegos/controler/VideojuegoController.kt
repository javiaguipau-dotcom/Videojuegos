package com.example.videojuegos.controler

import com.example.videojuegos.interfaces.InterfaceDao
import com.example.videojuegos.models.Videojuego

class VideojuegoController(private val dao: InterfaceDao) {

    fun cargarVideojuegos() = dao.getAll()

    fun borrar(id: Int, callback: (MutableList<Videojuego>) -> Unit) {
        dao.delete(id)
        callback(dao.getAll())
    }

    fun insertar(videojuego: Videojuego) =
        dao.insert(videojuego)

    fun editar(videojuego: Videojuego) =
        dao.update(videojuego)
}
