package com.example.cardflex.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun TarjetasEscaneadasScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Tarjetas Escaneadas",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Lista simulada de tarjetas
        repeat(5) { index ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .clickable {
                        navController.navigate("detalle_tarjeta")
                    }
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Nombre ${index + 1}")
                    Text("empresa${index + 1}@correo.com")
                    Text("Tel: 449 000 00${index}")
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = {
            navController.navigate("descargar_formato")
        }) {
            Text("Descargar lista")
        }

    }
}
