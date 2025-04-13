package com.example.cardflex.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun EnviarCorreoScreen(navController: NavHostController) {
    var mensaje by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Text(text = "Enviar correo a: juan.perez@correo.com", style = MaterialTheme.typography.titleMedium)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = mensaje,
            onValueChange = { mensaje = it },
            label = { Text("Escribe tu mensaje") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = { navController.popBackStack() }) {
            Text("Enviar (simulado)")
        }
    }
}
