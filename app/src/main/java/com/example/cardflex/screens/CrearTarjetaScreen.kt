package com.example.cardflex.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun CrearTarjetaScreen(navController: NavHostController) {
    var nombre by remember { mutableStateOf("") }
    var apellido by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Crear Tarjeta Digital",
            style = MaterialTheme.typography.headlineSmall,
            fontSize = 22.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = apellido,
            onValueChange = { apellido = it },
            label = { Text("Apellido") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = telefono,
            onValueChange = { telefono = it },
            label = { Text("Teléfono") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = correo,
            onValueChange = { correo = it },
            label = { Text("Correo electrónico") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                navController.navigate("confirmacion_contacto")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar tarjeta")
        }
    }
}
