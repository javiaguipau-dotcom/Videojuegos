package com.example.videojuegos.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.videojuegos.R
import com.example.videojuegos.models.Videojuego

class AdapterVideojuegos(
    private var lista: MutableList<Videojuego>,
    private val onDelete: (Int) -> Unit
) : RecyclerView.Adapter<ViewVideojuegos>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewVideojuegos {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_videojuego, parent, false)
        return ViewVideojuegos(v) // ⬅️ Creamos una instancia de ViewVideojuegos
    }

    override fun onBindViewHolder(holder: ViewVideojuegos, position: Int) {
        val item = lista[position]

        holder.nombre.text = item.nombre
        holder.descripcion.text = item.descripcion

        Glide.with(holder.itemView.context)
            .load(item.imagenUrl)
            .into(holder.img)

        holder.btnBorrar.setOnClickListener {
            onDelete(item.id)
        }
    }

    override fun getItemCount() = lista.size

    fun actualizarLista(nuevaLista: MutableList<Videojuego>) {
        lista = nuevaLista
        notifyDataSetChanged()
    }
}
