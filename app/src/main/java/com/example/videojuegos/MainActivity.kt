package com.example.videojuegos

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.activity.result.contract.ActivityResultContracts
import com.example.videojuegos.adapter.AdapterVideojuegos

import com.example.videojuegos.controler.VideojuegoController
import com.example.videojuegos.dao.DaoVideojuegos



class MainActivity : AppCompatActivity() {

    lateinit var controller: VideojuegoController
    lateinit var adapter: AdapterVideojuegos

    // Constante para la clave del ID que pasaremos
    companion object {
        const val VIDEOJUEGO_ID_KEY = "videojuego_id"
    }

    private val activityResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                adapter.actualizarLista(controller.cargarVideojuegos())
                Toast.makeText(this, "Lista actualizada.", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 1. INICIALIZAR PERSISTENCIA Y CARGAR DATOS
        DaoVideojuegos.inicializar(applicationContext)

        // 2. INICIALIZAR EL CONTROLADOR
        controller = VideojuegoController(DaoVideojuegos)

        // 3. CONFIGURAR RECYCLERVIEW Y ADAPTER
        val recycler = findViewById<RecyclerView>(R.id.recyclerView)
        recycler.layoutManager = LinearLayoutManager(this)

        adapter = AdapterVideojuegos(
            listaVideojuegos = controller.cargarVideojuegos(),
            // onDeleteClickListener
            onDeleteClickListener = { id ->
                controller.borrar(id) { nuevaLista ->
                    adapter.actualizarLista(nuevaLista)
                    Toast.makeText(this, "Videojuego eliminado.", Toast.LENGTH_SHORT).show()
                }
            },
            // ⬅️ onEditClickListener
            onEditClickListener = { id ->
                val intent = Intent(this, FormularioVideojuegoActivity::class.java).apply {
                    // Pasamos el ID para entrar en modo Edición
                    putExtra(VIDEOJUEGO_ID_KEY, id)
                }
                activityResultLauncher.launch(intent)
            }
        )
        recycler.adapter = adapter

        // 4. CONFIGURAR BOTÓN FAB (Modo Alta)
        val fab = findViewById<FloatingActionButton>(R.id.fabAdd)
        fab.setOnClickListener {
            val intent = Intent(this, FormularioVideojuegoActivity::class.java)
            // No se pasa ID, entra en modo Alta
            activityResultLauncher.launch(intent)
        }
    }
}
