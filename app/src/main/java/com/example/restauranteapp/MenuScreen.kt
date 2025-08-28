package com.example.restauranteapp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.restauranteapp.models.MenuViewModel

@Composable
fun MenuScreen(
    viewModel: MenuViewModel = viewModel(),
    onAddClick: () -> Unit,
    onEditClick: (Long) -> Unit
) {
    val menuItems by viewModel.menuItems.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchMenuItems()
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onAddClick) {
                Icon(Icons.Default.Add, contentDescription = "Agregar Ítem")
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            items(menuItems) { item ->
                MenuItemCard(
                    item = item,
                    onEditClick = { onEditClick(item.id!!) }, // Ahora le decimos que el ID no es nulo
                    onDeleteClick = { viewModel.deleteItem(item.id!!) }
                )
            }
        }
    }
}

@Composable
fun MenuItemCard(item: MenuItem, onEditClick: (MenuItem) -> Unit, onDeleteClick: (MenuItem) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = item.item_name, style = MaterialTheme.typography.titleMedium)
                Text(text = item.item_description ?: "Sin descripción", style = MaterialTheme.typography.bodySmall)
                Text(text = "$${item.item_price}", style = MaterialTheme.typography.bodyMedium)
            }
            IconButton(onClick = { onEditClick(item) }) {
                Icon(Icons.Default.Edit, contentDescription = "Editar")
            }
            IconButton(onClick = { onDeleteClick(item) }) {
                Icon(Icons.Default.Delete, contentDescription = "Eliminar")
            }
        }
    }
}