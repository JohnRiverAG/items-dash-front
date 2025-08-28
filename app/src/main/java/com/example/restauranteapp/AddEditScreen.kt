package com.example.restauranteapp

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.restauranteapp.models.MenuViewModel

@Composable
fun AddEditScreen(
    viewModel: MenuViewModel = viewModel(),
    menuItem: MenuItem? = null,
    onItemSaved: () -> Unit
) {
    val context = LocalContext.current
    val errorMessage by viewModel.errorMessage.collectAsState()

    // Observar los errores y mostrarlos
    LaunchedEffect(errorMessage) {
        errorMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        }
    }

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
                val newItem = MenuItem(
                    id = if (menuItem == null) null else menuItem.id,
                    item_name = name,
                    item_description = description,
                    item_price = newPrice
                )

                if (menuItem == null) {
                    // Llama a la nueva función del ViewModel
                    viewModel.createItem(newItem) {
                        // Código que se ejecuta al finalizar
                        Toast.makeText(context, "¡Ítem creado con éxito!", Toast.LENGTH_SHORT).show()
                        onItemSaved() // Navega de vuelta
                    }
                }
                // ... (lógica para editar)
            },
            // ...
        ){
            Text(text = if (menuItem == null) "Guardar" else "Actualizar")
        }
    }
}