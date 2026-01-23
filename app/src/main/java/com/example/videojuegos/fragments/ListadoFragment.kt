package com.example.videojuegos.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.videojuegos.R
import com.example.videojuegos.adapter.AdapterVideojuegos
import com.example.videojuegos.controler.VideojuegoController
import com.example.videojuegos.dao.DaoVideojuegos
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ListadoFragment : Fragment(R.layout.fragment_listado) {

    private lateinit var adapter: AdapterVideojuegos
    private lateinit var controller: VideojuegoController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        controller = VideojuegoController(DaoVideojuegos)

        val recycler = view.findViewById<RecyclerView>(R.id.rvVideojuegos)
        recycler.layoutManager = LinearLayoutManager(requireContext())

        // Asegúrate de que tu Adapter acepte el nuevo OnClickListener para el detalle si lo necesitas
        adapter = AdapterVideojuegos(
            listaVideojuegos = controller.cargarVideojuegos(),
            onDeleteClickListener = { id ->
                controller.borrar(id) { nuevaLista ->
                    adapter.actualizarLista(nuevaLista)
                }
            },
            onEditClickListener = { id ->
                // Navegar al Formulario (Edición)
                val action = ListadoFragmentDirections.actionListadoToFormulario(id)
                findNavController().navigate(action)
            }
            // Si quieres añadir click al detalle, deberías pasarlo aquí también
        )
        recycler.adapter = adapter

        // Botón Flotante para añadir (ID = -1)
        view.findViewById<FloatingActionButton>(R.id.fabAdd).setOnClickListener {
            // Navegar al Formulario (Nuevo)
            val action = ListadoFragmentDirections.actionListadoToFormulario(-1)
            findNavController().navigate(action)
        }
    }

    // OPCIONAL: Refrescar la lista al volver de otra pantalla
    override fun onResume() {
        super.onResume()
        if (::adapter.isInitialized) {
            adapter.actualizarLista(controller.cargarVideojuegos())
        }
    }
}