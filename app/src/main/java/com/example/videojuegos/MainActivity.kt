package com.example.videojuegos


import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.videojuegos.controler.VideojuegoController
import com.example.videojuegos.dao.DaoVideojuegos
import com.example.videojuegos.adapter.AdapterVideojuegos
import com.google.android.material.floatingactionbutton.FloatingActionButton
class MainActivity : AppCompatActivity() {

    lateinit var controller: VideojuegoController
    lateinit var adapter: AdapterVideojuegos

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        controller = VideojuegoController(DaoVideojuegos())

        val recycler = findViewById<RecyclerView>(R.id.recyclerView)
        recycler.layoutManager = LinearLayoutManager(this)

        adapter = AdapterVideojuegos(
            controller.cargarVideojuegos()
        ) { id ->
            controller.borrar(id) { nuevaLista ->
                adapter.actualizarLista(nuevaLista)
            }
        }

        recycler.adapter = adapter

        val fab = findViewById<FloatingActionButton>(R.id.fabAdd)
        fab.setOnClickListener {
            Toast.makeText(this, "Función de ALTA no implementada", Toast.LENGTH_SHORT).show()
        }
    }
}
