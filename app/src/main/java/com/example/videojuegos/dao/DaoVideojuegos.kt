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

    // Datos iniciales (usados solo si el archivo no existe)
    private val defaultList = mutableListOf(
        Videojuego(1, "The Legend of Zelda: Tears of the Kingdom", "Su trama central narra las heroicas misiones de Link para salvar Hyrule.", "https://via.placeholder.com/150?text=Zelda", 4),
        Videojuego(2, "Super Mario Bros. Wonder", "Mario es el icónico fontanero italiano, mascota de Nintendo, en una aventura floral.", "https://via.placeholder.com/150?text=Mario", 5),
        Videojuego(3, "Elden Ring", "Un juego de rol de acción épico ambientado en las Tierras Intermedias.", "https://via.placeholder.com/150?text=EldenRing", 5)
    )

    /**
     * Debe ser llamado al inicio de la aplicación para cargar los datos.
     */
    fun inicializar(context: Context) {
        appContext = context.applicationContext
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

    override fun getAll(): List<Videojuego> = lista.toList()

    override fun getById(id: Int): Videojuego? {
        return lista.find { it.id == id }
    }

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
}