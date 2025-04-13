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
fun InfoUsuarioScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Información del usuario",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text("👤 Nombre: Usuario Demo", fontSize = 18.sp)
        Text("📧 Correo: usuario@ejemplo.com", fontSize = 18.sp)
        Text("🔐 Rol: Administrador", fontSize = 18.sp)

        Spacer(modifier = Modifier.height(32.dp))

        Button(onClick = { navController.popBackStack() }) {
            Text("Regresar")
        }
    }
}
