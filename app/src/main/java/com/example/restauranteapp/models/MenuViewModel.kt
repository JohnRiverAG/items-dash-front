package com.example.restauranteapp.models

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.restauranteapp.MenuItem
import com.example.restauranteapp.network.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MenuViewModel : ViewModel() {
    private val _menuItems = MutableStateFlow<List<MenuItem>>(emptyList())
    val menuItems: StateFlow<List<MenuItem>> = _menuItems

    // Nuevo: Estado para manejar mensajes de error
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun fetchMenuItems() {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getAllItems()
                if (response.isSuccessful) {
                    _menuItems.value = response.body() ?: emptyList()
                    _errorMessage.value = null // Limpiar el error si la llamada es exitosa
                } else {
                    val errorBody = response.errorBody()?.string()
                    _errorMessage.value = "Error al obtener ítems: ${response.code()}"
                    Log.e("API_CALL", "Error al obtener ítems: ${response.code()} - $errorBody")
                }
            } catch (e: Exception) {
                _errorMessage.value = "Error de red: ${e.message}"
                Log.e("API_CALL", "Excepción de red: ${e.message}", e)
            }
        }
    }

    fun createItem(item: MenuItem) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.createItem(item)
                if (response.isSuccessful) {
                    fetchMenuItems()
                    _errorMessage.value = null
                } else {
                    val errorBody = response.errorBody()?.string()
                    _errorMessage.value = "Error al crear ítem: ${response.code()}"
                    Log.e("API_CALL", "Error al crear ítem: ${response.code()} - $errorBody")
                }
            } catch (e: Exception) {
                _errorMessage.value = "Error de red: ${e.message}"
                Log.e("API_CALL", "Excepción al crear ítem: ${e.message}", e)
            }
        }
    }

    fun updateItem(item: MenuItem) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.updateItem(item.id, item)
                if (response.isSuccessful) {
                    fetchMenuItems()
                    _errorMessage.value = null
                } else {
                    _errorMessage.value = "Error al actualizar ítem: ${response.code()}"
                    Log.e("API_CALL", "Error al actualizar ítem: ${response.code()}")
                }
            } catch (e: Exception) {
                _errorMessage.value = "Error de red: ${e.message}"
                Log.e("API_CALL", "Excepción al actualizar ítem: ${e.message}", e)
            }
        }
    }

    fun deleteItem(id: Long) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.deleteItem(id)
                if (response.isSuccessful) {
                    fetchMenuItems()
                    _errorMessage.value = null
                } else {
                    _errorMessage.value = "Error al eliminar ítem: ${response.code()}"
                    Log.e("API_CALL", "Error al eliminar ítem: ${response.code()}")
                }
            } catch (e: Exception) {
                _errorMessage.value = "Error de red: ${e.message}"
                Log.e("API_CALL", "Excepción al eliminar ítem: ${e.message}", e)
            }
        }
    }
}