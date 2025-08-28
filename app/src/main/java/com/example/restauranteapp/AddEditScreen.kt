package com.example.restauranteapp

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.restauranteapp.models.MenuViewModel

@Composable
fun AddEditScreen(
    viewModel: MenuViewModel = viewModel(),
    menuItem: MenuItem? = null, // Usado para editar
    onItemSaved: () -> Unit // Función para volver a la pantalla anterior
) {
    var name by remember { mutableStateOf(menuItem?.item_name ?: "") }
    var description by remember { mutableStateOf(menuItem?.item_description ?: "") }
    var price by remember { mutableStateOf(menuItem?.item_price?.toString() ?: "") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(text = if (menuItem == null) "Agregar Nuevo Ítem" else "Editar Ítem")

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nombre del Ítem") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Descripción") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = price,
            onValueChange = { price = it },
            label = { Text("Precio") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                val newPrice = price.toDoubleOrNull() ?: 0.0
                if (menuItem == null) {
                    // Si no hay un ítem, creamos uno nuevo con un ID temporal
                    val newItem = MenuItem(0, name, description, newPrice)
                    viewModel.createItem(newItem)
                } else {
                    // Si hay un ítem, lo actualizamos
                    val updatedItem = menuItem.copy(item_name = name, item_description = description, item_price = newPrice)
                    viewModel.updateItem(updatedItem)
                }
                onItemSaved()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = if (menuItem == null) "Guardar" else "Actualizar")
        }
    }
}