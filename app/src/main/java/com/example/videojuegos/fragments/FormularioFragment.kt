package com.example.videojuegos.fragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.videojuegos.R
import com.example.videojuegos.controler.VideojuegoController
import com.example.videojuegos.dao.DaoVideojuegos
import com.example.videojuegos.models.Videojuego

class FormularioFragment : Fragment(R.layout.activity_add_videojuego) {

    // 1. Argumentos Seguros (Safe Args)
    private val args: FormularioFragmentArgs by navArgs()

    private lateinit var controller: VideojuegoController
    private var imagenUri: Uri? = null
    private var juegoAEditar: Videojuego? = null

    // Referencias a las vistas
    private lateinit var etNombre: EditText
    private lateinit var etDescripcion: EditText
    private lateinit var ratingBarForm: RatingBar
    private lateinit var imgPrevisualizacion: ImageView
    private lateinit var btnGuardar: Button

    // 2. Launcher para la Galería (Adaptado para Fragment)
    private val selectImageLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            imagenUri = result.data?.data
            imagenUri?.let { uri ->
                // Permisos persistentes
                val takeFlags: Int = Intent.FLAG_GRANT_READ_URI_PERMISSION
                requireContext().contentResolver.takePersistableUriPermission(uri, takeFlags)

                Glide.with(this).load(uri).into(imgPrevisualizacion)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inicializar controlador
        controller = VideojuegoController(DaoVideojuegos)

        // 3. Vincular Vistas
        etNombre = view.findViewById(R.id.txtNombre)
        etDescripcion = view.findViewById(R.id.txtDescripcion)
        ratingBarForm = view.findViewById(R.id.ratingBarPuntuacion)
        imgPrevisualizacion = view.findViewById(R.id.imgPrevisualizacion)
        btnGuardar = view.findViewById(R.id.btnGuardarVideojuego)
        val btnSeleccionar = view.findViewById<Button>(R.id.btnSeleccionarImagen)

        // 4. Lógica de Edición vs Alta
        val videojuegoId = args.videojuegoId
        if (videojuegoId != -1) {
            juegoAEditar = controller.cargarVideojuegoPorId(videojuegoId)
            cargarDatosParaEdicion()
            btnGuardar.text = "Guardar Cambios"
        }

        // 5. Click: Seleccionar Imagen
        btnSeleccionar.setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type = "image/*"
                flags = Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION
            }
            selectImageLauncher.launch(intent)
        }

        // 6. Click: Guardar
        btnGuardar.setOnClickListener {
            guardarVideojuego()
        }
    }

    private fun cargarDatosParaEdicion() {
        juegoAEditar?.let { juego ->
            etNombre.setText(juego.nombre)
            etDescripcion.setText(juego.descripcion)
            ratingBarForm.rating = juego.puntuacion.toFloat()
            Glide.with(this).load(juego.imagenUrl).into(imgPrevisualizacion)
            imagenUri = Uri.parse(juego.imagenUrl)
        }
    }

    private fun guardarVideojuego() {
        val nombre = etNombre.text.toString()
        val descripcion = etDescripcion.text.toString()
        val puntuacion = ratingBarForm.rating.toInt()
        val imagenPath = imagenUri?.toString() ?: juegoAEditar?.imagenUrl ?: ""

        if (nombre.isBlank() || descripcion.isBlank() || imagenPath.isBlank()) {
            Toast.makeText(requireContext(), "Completa todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        if (juegoAEditar != null) {
            // EDITAR
            val actualizado = juegoAEditar!!.copy(
                nombre = nombre,
                descripcion = descripcion,
                imagenUrl = imagenPath,
                puntuacion = puntuacion
            )
            controller.editar(actualizado)
            Toast.makeText(requireContext(), "Actualizado con éxito", Toast.LENGTH_SHORT).show()
        } else {
            // INSERTAR
            val newId = (controller.cargarVideojuegos().maxOfOrNull { it.id } ?: 0) + 1
            val nuevo = Videojuego(newId, nombre, descripcion, imagenPath, puntuacion)
            controller.insertar(nuevo)
            Toast.makeText(requireContext(), "Añadido con éxito", Toast.LENGTH_SHORT).show()
        }

        // 7. Navegación hacia atrás (Vuelve automáticamente al listado)
        findNavController().popBackStack()
    }
}