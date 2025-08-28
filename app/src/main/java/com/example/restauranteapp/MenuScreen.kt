package com.example.restauranteapp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.restauranteapp.models.MenuViewModel

@Composable
fun MenuScreen(viewModel: MenuViewModel) {
    val menuItems by viewModel.menuItems.collectAsState()

    // Cargar los ítems al inicio
    LaunchedEffect(Unit) {
        viewModel.fetchMenuItems()
    }

    Column {
        LazyColumn {
            items(menuItems) { item ->
                Text(text = "Nombre: ${item.item_name}")
                Text(text = "Precio: $${item.item_price}")
            }
        }
        Button(onClick = { /* Navegar a la pantalla para agregar/editar */ }) {
            Text(text = "Agregar nuevo ítem")
        }
    }
}
