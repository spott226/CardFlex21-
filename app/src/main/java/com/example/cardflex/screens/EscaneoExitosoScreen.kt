package com.example.cardflex.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun EscaneoExitosoScreen(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF4CAF50)),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "✅ Escaneo Exitoso",
                color = Color.White,
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = { navController.navigate("tarjetas_escaneadas") },
                colors = ButtonDefaults.buttonColors(containerColor = Color.White)
            ) {
                Text("Ver tarjetas escaneadas", color = Color.Black)
            }
        }
    }
}
