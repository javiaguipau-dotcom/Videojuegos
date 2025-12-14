package com.example.videojuegos.adapter // ⬅️ Paquete del adaptador

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.videojuegos.R // ⬅️ ¡CRUCIAL! Importa R del paquete base de la aplicación
import com.example.videojuegos.models.Videojuego

typealias VideojuegoList = List<Videojuego>

class AdapterVideojuegos(
    private var listaVideojuegos: VideojuegoList,
    private val onDeleteClickListener: (Int) -> Unit,
    private val onEditClickListener: (Int) -> Unit
) : RecyclerView.Adapter<AdapterVideojuegos.VideojuegoViewHolder>() {

    inner class VideojuegoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgJuego: ImageView = itemView.findViewById(R.id.imgJuego)
        val txtNombre: TextView = itemView.findViewById(R.id.txtNombre)
        val txtDescripcion: TextView = itemView.findViewById(R.id.txtDescripcion)
        val ratingBarPuntuacion: RatingBar = itemView.findViewById(R.id.ratingBarPuntuacion)
        val btnBorrar: ImageButton = itemView.findViewById(R.id.btnBorrar)
        val btnEditar: ImageButton = itemView.findViewById(R.id.btnEditar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideojuegoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_videojuego, parent, false)
        return VideojuegoViewHolder(view)
    }

    override fun onBindViewHolder(holder: VideojuegoViewHolder, position: Int) {
        val videojuego = listaVideojuegos[position]

        // Cargar imagen
        Glide.with(holder.itemView.context)
            .load(videojuego.imagenUrl)
            // Asegúrate de que ic_launcher_foreground exista en R.drawable
            .placeholder(R.drawable.ic_launcher_foreground)
            .error(R.drawable.ic_launcher_foreground)
            .into(holder.imgJuego)

        // Asignar datos
        holder.txtNombre.text = videojuego.nombre
        holder.txtDescripcion.text = videojuego.descripcion
        holder.ratingBarPuntuacion.rating = videojuego.puntuacion.toFloat()

        // Listeners
        holder.btnBorrar.setOnClickListener {
            onDeleteClickListener(videojuego.id)
        }
        holder.btnEditar.setOnClickListener {
            onEditClickListener(videojuego.id)
        }
    }

    override fun getItemCount(): Int {
        return listaVideojuegos.size
    }

    fun actualizarLista(nuevaLista: VideojuegoList) {
        listaVideojuegos = nuevaLista
        notifyDataSetChanged()
    }
}
