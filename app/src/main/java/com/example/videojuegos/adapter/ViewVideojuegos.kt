package com.example.videojuegos.adapter

import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.videojuegos.R

class ViewVideojuegos(v: View) : RecyclerView.ViewHolder(v) {
    val img: ImageView = v.findViewById(R.id.imgJuego)
    val nombre: TextView = v.findViewById(R.id.txtNombre)
    val descripcion: TextView = v.findViewById(R.id.txtDescripcion)
    val btnBorrar: ImageButton = v.findViewById(R.id.btnBorrar)
}