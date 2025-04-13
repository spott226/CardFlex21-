package com.example.cardflex.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun GaleriaQrScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Galería de QR registrados",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(24.dp))

        repeat(3) { index ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .clickable {
                        navController.navigate("detalle_tarjeta")
                    }
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.QrCode,
                        contentDescription = "Icono QR",
                        modifier = Modifier.size(32.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text("QR ${index + 1}", style = MaterialTheme.typography.titleMedium)
                        Text("Nombre de ejemplo", style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
        }
    }
}
