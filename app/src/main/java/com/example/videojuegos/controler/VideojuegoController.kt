package com.example.videojuegos.controler


import com.example.videojuegos.interfaces.InterfaceDao
import com.example.videojuegos.models.Videojuego

typealias VideojuegoList = List<Videojuego>

class VideojuegoController(private val dao: InterfaceDao) {

    fun cargarVideojuegos(): VideojuegoList = dao.getAll()

    fun cargarVideojuegoPorId(id: Int) = dao.getById(id) // ⬅️ Nuevo

    fun borrar(id: Int, callback: (VideojuegoList) -> Unit) {
        dao.delete(id)
        callback(dao.getAll())
    }

    fun insertar(videojuego: Videojuego) = dao.insertar(videojuego)

    fun editar(videojuego: Videojuego) = dao.update(videojuego) // ⬅️ Nuevo
}