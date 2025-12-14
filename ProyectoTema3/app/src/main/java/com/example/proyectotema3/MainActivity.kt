package com.example.proyectotema3

import com.example.proyectotema3.models.Videojuego

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectotema3.adapter.AdapterVideojuegos
import com.example.proyectotema3.controler.VideojuegoController
import com.google.android.material.floatingactionbutton.FloatingActionButton
class MainActivity : AppCompatActivity() {

    private lateinit var recycler: RecyclerView
    private lateinit var adapter: AdapterVideojuegos
    private val controller = VideojuegoController()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recycler = findViewById(R.id.recyclerVideojuegos)

        // Datos de ejemplo
        controller.insertar(
            Videojuego(
                1,
                "The Legend of Zelda: Breath of the Wild",
                "Nintendo Switch",
                2017,
                "https://i.imgur.com/oR25S9K.jpeg"
            )
        )

        controller.insertar(
            Videojuego(
                2,
                "God of War Ragnarök",
                "PlayStation 5",
                2022,
                "https://i.imgur.com/wJYbU4R.jpeg"
            )
        )

        adapter = AdapterVideojuegos(controller.getListado().toMutableList()) { videojuego ->
            controller.borrar(videojuego.id) {
                adapter.notifyDataSetChanged()
            }
        }

        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(this)

        findViewById<FloatingActionButton>(R.id.fabAdd).setOnClickListener {
            Toast.makeText(this, "Añadir videojuego (no implementado)", Toast.LENGTH_SHORT).show()
        }
    }
}
