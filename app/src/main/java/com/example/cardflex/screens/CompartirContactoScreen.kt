package com.example.cardflex.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun CompartirContactoScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Compartir contacto",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text("Simulación: el contacto ha sido 'compartido' con éxito.", style = MaterialTheme.typography.bodyMedium)

        Spacer(modifier = Modifier.height(32.dp))

        Button(onClick = { navController.popBackStack() }) {
            Text("Aceptar")
        }
    }
}
