

//Archivo con el modelo de datos de la BD
package com.example.restauranteapp

data class MenuItem(
    val id: Long? = null, // Hacer el ID opcional para nuevos ítems
    val item_name: String,
    val item_description: String?,
    val item_price: Double
)