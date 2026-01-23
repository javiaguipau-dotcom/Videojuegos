package com.example.videojuegos.fragments

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.videojuegos.R
import com.example.videojuegos.dao.DaoVideojuegos

class DetalleFragment : Fragment(R.layout.fragment_detalle) {

    private val args: DetalleFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val juego = DaoVideojuegos.getById(args.videojuegoId)

        juego?.let {
            view.findViewById<TextView>(R.id.tvNombreDetalle).text = it.nombre
            view.findViewById<TextView>(R.id.tvDescripcionDetalle).text = it.descripcion
            view.findViewById<RatingBar>(R.id.rbPuntuacionDetalle).rating = it.puntuacion.toFloat()

            val imagen = view.findViewById<ImageView>(R.id.ivImagenDetalle)
            Glide.with(this).load(it.imagenUrl).into(imagen)
        }
    }
}