package com.example.videojuegos

import android.app.Activity
import android.content.Intent
import android.net.Uri // ⬅️ Necesaria
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView // ⬅️ Necesaria
import android.widget.EditText
import android.widget.RatingBar
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts // ⬅️ Necesaria
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.videojuegos.controler.VideojuegoController
import com.example.videojuegos.dao.DaoVideojuegos
import com.example.videojuegos.models.Videojuego

class AddVideojuegoActivity : AppCompatActivity() {

    private lateinit var controller: VideojuegoController
    private var imagenUri: Uri? = null // ⬅️ Almacenará la URI local

    // 1. Registro para lanzar la galería y obtener el resultado
    private val selectImageLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            imagenUri = data?.data // Capturamos la URI seleccionada

            // 2. Muestra la previsualización usando Glide
            if (imagenUri != null) {
                val imgView = findViewById<ImageView>(R.id.imgPrevisualizacion)
                Glide.with(this).load(imagenUri).into(imgView)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_videojuego)

        controller = VideojuegoController(DaoVideojuegos)

        val btnGuardar = findViewById<Button>(R.id.btnGuardarVideojuego)
        val btnSeleccionar = findViewById<Button>(R.id.btnSeleccionarImagen)

        // 3. Configuración del botón para seleccionar la imagen
        btnSeleccionar.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
                type = "image/*" // Solo archivos de imagen
            }
            selectImageLauncher.launch(intent)
        }

        btnGuardar.setOnClickListener {

            val nombre = findViewById<EditText>(R.id.txtNombre).text.toString()
            val descripcion = findViewById<EditText>(R.id.txtDescripcion).text.toString()
            val puntuacion = findViewById<RatingBar>(R.id.ratingBarPuntuacion).rating.toInt()

            // ⚠️ Validación: Usar la URI guardada o una URL por defecto si no se seleccionó nada
            val imagenPath = imagenUri?.toString() ?: "URL_POR_DEFECTO_SI_NO_SELECCIONA"

            if (nombre.isBlank() || descripcion.isBlank() || imagenPath.isBlank()) {
                Toast.makeText(this, "Rellena los campos y selecciona una imagen.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val newId = (controller.cargarVideojuegos().maxOfOrNull { it.id } ?: 0) + 1

            val nuevoJuego = Videojuego(
                id = newId,
                nombre = nombre,
                descripcion = descripcion,
                imagenUrl = imagenPath, // Guardamos la URI o la URL por defecto
                puntuacion = puntuacion
            )

            controller.insertar(nuevoJuego)

            setResult(Activity.RESULT_OK)
            finish()
        }
    }
}