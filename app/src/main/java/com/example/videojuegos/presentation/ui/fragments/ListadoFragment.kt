package com.example.videojuegos.presentation.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.videojuegos.R
import com.example.videojuegos.presentation.adapter.AdapterVideojuegos
import com.example.videojuegos.presentation.viewmodel.VideojuegoViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListadoFragment : Fragment(R.layout.fragment_listado) {

    private val viewModel: VideojuegoViewModel by viewModels()
    private lateinit var adapter: AdapterVideojuegos

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recycler = view.findViewById<RecyclerView>(R.id.rvVideojuegos)
        recycler.layoutManager = LinearLayoutManager(requireContext())

        // Inicializar adapter vacío
        adapter = AdapterVideojuegos(
            listaVideojuegos = emptyList(),
            onDeleteClickListener = { id ->
                viewModel.eliminarVideojuego(id)
            },
            onEditClickListener = { id ->
                val action = ListadoFragmentDirections.actionListadoToFormulario(id)
                findNavController().navigate(action)
            }
        )
        recycler.adapter = adapter

        // Observar cambios en la lista de videojuegos
        viewModel.videojuegos.observe(viewLifecycleOwner) { videojuegos ->
            adapter.actualizarLista(videojuegos)
        }

        // Observar errores
        viewModel.errorMessage.observe(viewLifecycleOwner) { error ->
            if (!error.isNullOrEmpty()) {
                // Aquí puedes mostrar un Toast o Snackbar
                android.widget.Toast.makeText(requireContext(), error, android.widget.Toast.LENGTH_SHORT).show()
            }
        }

        // Botón Flotante para añadir
        view.findViewById<FloatingActionButton>(R.id.fabAdd).setOnClickListener {
            val action = ListadoFragmentDirections.actionListadoToFormulario(-1)
            findNavController().navigate(action)
        }

        // Cargar videojuegos al crear la vista
        viewModel.cargarVideojuegos()
    }

    override fun onResume() {
        super.onResume()
        // Recargar la lista al volver
        viewModel.cargarVideojuegos()
    }
}
