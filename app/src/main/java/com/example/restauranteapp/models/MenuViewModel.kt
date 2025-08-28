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
    fun createItem(item: MenuItem) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.createItem(item)
                if (response.isSuccessful) {
                    fetchMenuItems() // Vuelve a cargar la lista para ver el nuevo ítem
                }
            } catch (e: Exception) {
                // Manejar el error
            }
        }
    }

    fun updateItem(item: MenuItem) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.updateItem(item.id, item)
                if (response.isSuccessful) {
                    fetchMenuItems() // Vuelve a cargar la lista para ver los cambios
                }
            } catch (e: Exception) {
                // Manejar el error
            }
        }
    }

    fun deleteItem(id: Long) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.deleteItem(id)
                if (response.isSuccessful) {
                    fetchMenuItems() // Vuelve a cargar la lista sin el ítem borrado
                }
            } catch (e: Exception) {
                // Manejar el error
            }
        }
    }