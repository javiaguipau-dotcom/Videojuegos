package com.example.proyectotema3.adapter

import com.bumptech.glide.Glide
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectotema3.models.Videojuego
import android.widget.ImageView
import android.widget.TextView
import android.widget.ImageButton
import android.view.ViewGroup
class AdapterVideojuegos(
    private val lista: MutableList<Videojuego>,
    private val onDelete: (Videojuego) -> Unit
) : RecyclerView.Adapter<AdapterVideojuegos.ViewHolder>() {

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val portada = v.findViewById<ImageView>(R.id.imgPortada)
        val titulo = v.findViewById<TextView>(R.id.txtTitulo)
        val plataforma = v.findViewById<TextView>(R.id.txtPlataforma)
        val año = v.findViewById<TextView>(R.id.txtAño)
        val borrar = v.findViewById<ImageButton>(R.id.btnBorrar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_videojuego, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val juego = lista[position]

        holder.titulo.text = juego.titulo
        holder.plataforma.text = "Plataforma: ${juego.plataforma}"
        holder.año.text = "Año: ${juego.año}"

        Glide.with(holder.itemView.context)
            .load(juego.imagenUrl)
            .into(holder.portada)

        holder.borrar.setOnClickListener {
            onDelete(juego)
        }
    }

    override fun getItemCount() = lista.size
}
