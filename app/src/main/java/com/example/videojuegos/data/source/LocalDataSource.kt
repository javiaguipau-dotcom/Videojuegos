package com.example.videojuegos.data.source

import android.content.Context
import com.example.videojuegos.domain.models.Videojuego
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File

class LocalDataSource(context: Context) {

    private val appContext = context.applicationContext
    private val FILE_NAME = "videojuegos_data.json"
    private var lista = mutableListOf<Videojuego>()

    private val defaultList = mutableListOf(
        Videojuego(1, "The Legend of Zelda", "Misiones de Link para salvar Hyrule.", "https://via.placeholder.com/150", 4),
        Videojuego(2, "Super Mario Bros. Wonder", "Aventura floral de Mario.", "https://via.placeholder.com/150", 5),
        Videojuego(3, "Elden Ring", "Juego de rol épico.", "https://via.placeholder.com/150", 5)
    )

    init {
        cargarDatos()
    }

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

    // MÉTODOS LOCALES
    fun getAll(): List<Videojuego> = lista.toList()

    fun getById(id: Int): Videojuego? = lista.find { it.id == id }

    fun save(videojuego: Videojuego): Boolean {
        return try {
            lista.add(videojuego)
            guardarDatos()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun update(videojuego: Videojuego): Boolean {
        return try {
            val index = lista.indexOfFirst { it.id == videojuego.id }
            if (index != -1) {
                lista[index] = videojuego
                guardarDatos()
                true
            } else {
                false
            }
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun delete(id: Int): Boolean {
        return try {
            val result = lista.removeIf { it.id == id }
            if (result) {
                guardarDatos()
            }
            result
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}
