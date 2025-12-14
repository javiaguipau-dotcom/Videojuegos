package com.example.videojuegos

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.* import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.videojuegos.controler.VideojuegoController
import com.example.videojuegos.dao.DaoVideojuegos
import com.example.videojuegos.models.Videojuego
import com.example.videojuegos.MainActivity.Companion.VIDEOJUEGO_ID_KEY

class FormularioVideojuegoActivity : AppCompatActivity() {

    private lateinit var controller: VideojuegoController
    private var imagenUri: Uri? = null
    private var juegoAEditar: Videojuego? = null

    private lateinit var etNombre: EditText
    private lateinit var etDescripcion: EditText
    private lateinit var ratingBarForm: RatingBar
    private lateinit var imgPrevisualizacion: ImageView
    private lateinit var btnGuardar: Button

    private val selectImageLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            imagenUri = data?.data

            if (imagenUri != null) {
                // Persistencia del permiso de lectura de URI
                val contentResolver = applicationContext.contentResolver
                val takeFlags: Int = Intent.FLAG_GRANT_READ_URI_PERMISSION
                contentResolver.takePersistableUriPermission(imagenUri!!, takeFlags)

                Glide.with(this).load(imagenUri).into(imgPrevisualizacion)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_videojuego) // Usamos el layout único

        controller = VideojuegoController(DaoVideojuegos)

        // 1. Inicializar Vistas
        etNombre = findViewById(R.id.txtNombre)
        etDescripcion = findViewById(R.id.txtDescripcion)
        ratingBarForm = findViewById(R.id.ratingBarPuntuacion)
        imgPrevisualizacion = findViewById(R.id.imgPrevisualizacion)
        btnGuardar = findViewById(R.id.btnGuardarVideojuego)
        val btnSeleccionar = findViewById<Button>(R.id.btnSeleccionarImagen)

        // 2. Cargar datos si estamos en modo Edición
        val videojuegoId = intent.getIntExtra(VIDEOJUEGO_ID_KEY, -1)
        if (videojuegoId != -1) {
            juegoAEditar = controller.cargarVideojuegoPorId(videojuegoId)
            cargarDatosParaEdicion()
            btnGuardar.text = "Guardar Cambios"
        } else {
            btnGuardar.text = "Añadir Videojuego"
        }

        // 3. Configuración de la Galería
        btnSeleccionar.setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type = "image/*"
                flags = Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION
            }
            selectImageLauncher.launch(intent)
        }

        // 4. Lógica de Guardado/Actualización
        btnGuardar.setOnClickListener {

            val nombre = etNombre.text.toString()
            val descripcion = etDescripcion.text.toString()
            val puntuacion = ratingBarForm.rating.toInt()

            // Si no hay URI, usamos la que ya tiene el juego (edición) o una por defecto (alta)
            val imagenPath = imagenUri?.toString() ?: juegoAEditar?.imagenUrl ?: "https://via.placeholder.com/150?text=No+Image"

            if (nombre.isBlank() || descripcion.isBlank() || imagenPath.isBlank()) {
                Toast.makeText(this, "Rellena los campos y selecciona una imagen.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (juegoAEditar != null) {
                // Modo EDICIÓN
                val juegoActualizado = juegoAEditar!!.copy(
                    nombre = nombre,
                    descripcion = descripcion,
                    imagenUrl = imagenPath,
                    puntuacion = puntuacion
                )
                controller.editar(juegoActualizado)
                Toast.makeText(this, "Videojuego actualizado.", Toast.LENGTH_SHORT).show()
            } else {
                // Modo ALTA
                val newId = (controller.cargarVideojuegos().maxOfOrNull { it.id } ?: 0) + 1
                val nuevoJuego = Videojuego(newId, nombre, descripcion, imagenPath, puntuacion)
                controller.insertar(nuevoJuego)
            }

            setResult(Activity.RESULT_OK)
            finish()
        }
    }

    private fun cargarDatosParaEdicion() {
        juegoAEditar?.let { juego ->
            etNombre.setText(juego.nombre)
            etDescripcion.setText(juego.descripcion)
            ratingBarForm.rating = juego.puntuacion.toFloat()

            Glide.with(this).load(juego.imagenUrl).into(imgPrevisualizacion)

            // Establecer la URI existente para que la selección de galería la reemplace
            imagenUri = Uri.parse(juego.imagenUrl)
        }
    }
}