package com.example.restauranteapp.models

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

    fun fetchMenuItems() {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getAllItems()
                if (response.isSuccessful) {
                    _menuItems.value = response.body() ?: emptyList()
                } else {
                    // Manejar el error de la API
                }
            } catch (e: Exception) {
                // Manejar la excepción de red
            }
        }
    }

    // Funciones para CRUD (Create, Read, Update, Delete)
    fun createItem(item: MenuItem) { /* Lógica para crear un ítem */ }
    fun updateItem(item: MenuItem) { /* Lógica para actualizar un ítem */ }
    fun deleteItem(id: Long) { /* Lógica para eliminar un ítem */ }
}