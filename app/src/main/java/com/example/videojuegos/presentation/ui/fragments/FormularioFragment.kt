package com.example.videojuegos.presentation.ui.fragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.videojuegos.R
import com.example.videojuegos.domain.models.Videojuego
import com.example.videojuegos.presentation.viewmodel.VideojuegoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FormularioFragment : Fragment(R.layout.activity_add_videojuego) {

    private val viewModel: VideojuegoViewModel by viewModels()
    private val args: FormularioFragmentArgs by navArgs()

    private var imagenUri: Uri? = null
    private var juegoAEditar: Videojuego? = null

    // Referencias a las vistas
    private lateinit var etNombre: EditText
    private lateinit var etDescripcion: EditText
    private lateinit var ratingBarForm: RatingBar
    private lateinit var imgPrevisualizacion: ImageView
    private lateinit var btnGuardar: Button

    // Launcher para la Galería
    private val selectImageLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            imagenUri = result.data?.data
            imagenUri?.let { uri ->
                val takeFlags: Int = Intent.FLAG_GRANT_READ_URI_PERMISSION
                requireContext().contentResolver.takePersistableUriPermission(uri, takeFlags)
                Glide.with(this).load(uri).into(imgPrevisualizacion)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Vincular Vistas
        etNombre = view.findViewById(R.id.txtNombre)
        etDescripcion = view.findViewById(R.id.txtDescripcion)
        ratingBarForm = view.findViewById(R.id.ratingBarPuntuacion)
        imgPrevisualizacion = view.findViewById(R.id.imgPrevisualizacion)
        btnGuardar = view.findViewById(R.id.btnGuardarVideojuego)
        val btnSeleccionar = view.findViewById<Button>(R.id.btnSeleccionarImagen)

        // Observar errores
        viewModel.errorMessage.observe(viewLifecycleOwner) { error ->
            if (!error.isNullOrEmpty()) {
                Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
            }
        }

        // Lógica de Edición vs Alta
        val videojuegoId = args.videojuegoId
        if (videojuegoId != -1) {
            viewModel.cargarVideojuegoPorId(videojuegoId)
            viewModel.videojuegoDetalle.observe(viewLifecycleOwner) { videojuego ->
                if (videojuego != null) {
                    juegoAEditar = videojuego
                    cargarDatosParaEdicion(videojuego)
                    btnGuardar.text = "Guardar Cambios"
                }
            }
        }

        // Click: Seleccionar Imagen
        btnSeleccionar.setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type = "image/*"
                flags = Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION
            }
            selectImageLauncher.launch(intent)
        }

        // Click: Guardar
        btnGuardar.setOnClickListener {
            guardarVideojuego()
        }
    }

    private fun cargarDatosParaEdicion(videojuego: Videojuego) {
        etNombre.setText(videojuego.nombre)
        etDescripcion.setText(videojuego.descripcion)
        ratingBarForm.rating = videojuego.puntuacion.toFloat()
        Glide.with(this).load(videojuego.imagenUrl).into(imgPrevisualizacion)
        imagenUri = Uri.parse(videojuego.imagenUrl)
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
            viewModel.actualizarVideojuego(actualizado)
            Toast.makeText(requireContext(), "Actualizado con éxito", Toast.LENGTH_SHORT).show()
        } else {
            // INSERTAR - Generar nuevo ID
            val newId = (viewModel.videojuegos.value?.maxOfOrNull { it.id } ?: 0) + 1
            val nuevo = Videojuego(newId, nombre, descripcion, imagenPath, puntuacion)
            viewModel.agregarVideojuego(nuevo)
            Toast.makeText(requireContext(), "Añadido con éxito", Toast.LENGTH_SHORT).show()
        }

        findNavController().popBackStack()
    }
}
