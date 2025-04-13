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
fun EscanearQrScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Simulación de escaneo QR",
            color = Color.White,
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = { navController.navigate("escaneo_exitoso") },
            colors = ButtonDefaults.buttonColors(containerColor = Color.White)
        ) {
            Text(text = "Escanear ahora", color = Color.Black)
        }
    }
}
