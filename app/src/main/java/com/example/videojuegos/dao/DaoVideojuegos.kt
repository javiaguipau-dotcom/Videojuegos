package com.example.videojuegos.dao

import android.content.Context
import com.example.videojuegos.interfaces.InterfaceDao
import com.example.videojuegos.models.Videojuego
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.*

object DaoVideojuegos : InterfaceDao {

    private const val FILE_NAME = "videojuegos_data.json"
    private lateinit var appContext: Context
    private var lista = mutableListOf<Videojuego>()

    private val defaultList = mutableListOf(
        Videojuego(1, "The Legend of Zelda", "Misiones de Link para salvar Hyrule.", "https://via.placeholder.com/150", 4),
        Videojuego(2, "Super Mario Bros. Wonder", "Aventura floral de Mario.", "https://via.placeholder.com/150", 5),
        Videojuego(3, "Elden Ring", "Juego de rol épico.", "https://via.placeholder.com/150", 5)
    )

    fun inicializar(context: Context) {
        appContext = context.applicationContext
        cargarDatos()
    }

    // --- MÉTODOS DE PERSISTENCIA ---
    private fun cargarDatos() {
        try {
            val file = File(appContext.filesDir, FILE_NAME)
            if (file.exists() && file.length() > 0) {
                val json = file.bufferedReader().use { it.readText() }
                val type = object : TypeToken<MutableList<Videojuego>>() {}.type
                lista = Gson().fromJson(json, type)
            } else {
                lista.addAll(defaultList)
                guardarDatos()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            lista.addAll(defaultList)
        }
    }

    private fun guardarDatos() {
        try {
            val json = Gson().toJson(lista)
            val file = File(appContext.filesDir, FILE_NAME)
            file.writeText(json)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // --- IMPLEMENTACIÓN DE LA INTERFAZ ---
    override fun getAll(): List<Videojuego> = lista.toList()

    override fun getById(id: Int): Videojuego? = lista.find { it.id == id }

    override fun delete(id: Int) {
        lista.removeIf { it.id == id }
        guardarDatos()
    }

    override fun insertar(videojuego: Videojuego) {
        lista.add(videojuego)
        guardarDatos()
    }

    override fun update(videojuego: Videojuego) {
        val index = lista.indexOfFirst { it.id == videojuego.id }
        if (index != -1) {
            lista[index] = videojuego
            guardarDatos()
        }
    }

    // --- ALIAS PARA COMPATIBILIDAD CON FRAGMENTOS ---
    // Estas funciones hacen que tus fragmentos funcionen sin cambiar su código
    override fun obtenerTodos(): List<Videojuego> = getAll()

    override fun obtenerPorId(id: Int): Videojuego? = getById(id)

    override fun eliminar(id: Int) = delete(id)
}