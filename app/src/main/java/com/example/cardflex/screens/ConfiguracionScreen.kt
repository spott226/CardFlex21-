package com.example.cardflex.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun ConfiguracionScreen(navController: NavHostController) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        Text("Configuración", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { /* Simular cambio de idioma */ }) {
            Text("Cambiar idioma")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { /* Simular cerrar sesión */ }) {
            Text("Cerrar sesión")
        }
    }
}
