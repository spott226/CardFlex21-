package com.example.cardflex.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun DescargarFormatoScreen(navController: NavHostController) {
    var formatoSeleccionado by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Selecciona el formato de descarga",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { formatoSeleccionado = "PDF" },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("📄 Descargar como PDF")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { formatoSeleccionado = "XML" },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("📁 Descargar como XML")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { formatoSeleccionado = "CSV" },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("📊 Descargar como CSV")
        }

        if (formatoSeleccionado.isNotEmpty()) {
            Spacer(modifier = Modifier.height(32.dp))
            Text("✅ Se generará archivo en formato: $formatoSeleccionado")
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(onClick = { navController.popBackStack() }) {
            Text("Volver")
        }
    }
}
