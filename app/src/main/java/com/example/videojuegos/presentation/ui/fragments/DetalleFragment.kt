package com.example.videojuegos.presentation.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.videojuegos.R
import com.example.videojuegos.presentation.viewmodel.VideojuegoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetalleFragment : Fragment(R.layout.fragment_detalle) {

    private val viewModel: VideojuegoViewModel by viewModels()
    private val args: DetalleFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Cargar el videojuego por ID
        viewModel.cargarVideojuegoPorId(args.videojuegoId)

        // Observar los cambios del detalle
        viewModel.videojuegoDetalle.observe(viewLifecycleOwner) { juego ->
            juego?.let {
                view.findViewById<TextView>(R.id.tvNombreDetalle).text = it.nombre
                view.findViewById<TextView>(R.id.tvDescripcionDetalle).text = it.descripcion
                view.findViewById<RatingBar>(R.id.rbPuntuacionDetalle).rating = it.puntuacion.toFloat()

                val imagen = view.findViewById<ImageView>(R.id.ivImagenDetalle)
                Glide.with(this).load(it.imagenUrl).into(imagen)
            }
        }

        // Observar errores
        viewModel.errorMessage.observe(viewLifecycleOwner) { error ->
            if (!error.isNullOrEmpty()) {
                android.widget.Toast.makeText(requireContext(), error, android.widget.Toast.LENGTH_SHORT).show()
            }
        }
    }
}
