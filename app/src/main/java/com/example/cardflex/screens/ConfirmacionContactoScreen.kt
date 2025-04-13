package com.example.cardflex.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun ConfirmacionContactoScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "¡Contacto guardado!",
            style = MaterialTheme.typography.headlineMedium,
            fontSize = 24.sp
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                navController.navigate("dashboard") {
                    popUpTo("dashboard") { inclusive = true }
                }
            }
        ) {
            Text("Volver al inicio")
        }
    }
}
