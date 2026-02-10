package com.example.videojuegos.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.videojuegos.domain.models.Videojuego
import com.example.videojuegos.domain.usecase.AddVideojuegoUseCase
import com.example.videojuegos.domain.usecase.DeleteVideojuegoUseCase
import com.example.videojuegos.domain.usecase.GetAllVideojuegosUseCase
import com.example.videojuegos.domain.usecase.GetVideojuegoByIdUseCase
import com.example.videojuegos.domain.usecase.UpdateVideojuegoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VideojuegoViewModel @Inject constructor(
    private val getAllVideojuegosUseCase: GetAllVideojuegosUseCase,
    private val getVideojuegoByIdUseCase: GetVideojuegoByIdUseCase,
    private val addVideojuegoUseCase: AddVideojuegoUseCase,
    private val updateVideojuegoUseCase: UpdateVideojuegoUseCase,
    private val deleteVideojuegoUseCase: DeleteVideojuegoUseCase
) : ViewModel() {

    // LiveData - Estado de la lista
    private val _videojuegos = MutableLiveData<List<Videojuego>>()
    val videojuegos: LiveData<List<Videojuego>> = _videojuegos

    // LiveData - Videojuego seleccionado/detalle
    private val _videojuegoDetalle = MutableLiveData<Videojuego?>()
    val videojuegoDetalle: LiveData<Videojuego?> = _videojuegoDetalle

    // LiveData - Estados de carga
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    // LiveData - Mensajes de error
    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    // Cargar todos los videojuegos
    fun cargarVideojuegos() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val lista = getAllVideojuegosUseCase()
                _videojuegos.value = lista
                _errorMessage.value = null
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "Error al cargar videojuegos"
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Cargar un videojuego por ID
    fun cargarVideojuegoPorId(id: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val videojuego = getVideojuegoByIdUseCase(id)
                _videojuegoDetalle.value = videojuego
                _errorMessage.value = null
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "Error al cargar el videojuego"
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Agregar nuevo videojuego
    fun agregarVideojuego(videojuego: Videojuego) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val success = addVideojuegoUseCase(videojuego)
                if (success) {
                    cargarVideojuegos() // Recargar lista
                    _errorMessage.value = null
                } else {
                    _errorMessage.value = "Error al agregar el videojuego"
                }
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "Error al agregar el videojuego"
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Actualizar videojuego
    fun actualizarVideojuego(videojuego: Videojuego) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val success = updateVideojuegoUseCase(videojuego)
                if (success) {
                    cargarVideojuegos() // Recargar lista
                    _errorMessage.value = null
                } else {
                    _errorMessage.value = "Error al actualizar el videojuego"
                }
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "Error al actualizar el videojuego"
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Eliminar videojuego
    fun eliminarVideojuego(id: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val success = deleteVideojuegoUseCase(id)
                if (success) {
                    cargarVideojuegos() // Recargar lista
                    _errorMessage.value = null
                } else {
                    _errorMessage.value = "Error al eliminar el videojuego"
                }
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "Error al eliminar el videojuego"
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Limpiar errores
    fun limpiarError() {
        _errorMessage.value = null
    }
}
