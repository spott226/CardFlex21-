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
fun ContactoDetalleScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Detalle del contacto",
            style = MaterialTheme.typography.headlineSmall,
            fontSize = 22.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text("👤 Nombre: Juan Pérez", fontSize = 18.sp)
        Text("📞 Teléfono: 449 123 4567", fontSize = 18.sp)
        Text("📧 Correo: juan.perez@correo.com", fontSize = 18.sp)
        Text("🏢 Empresa: Ejemplo S.A.", fontSize = 18.sp)

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { navController.navigate("llamada_simulada") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("📞 Simular llamada")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { navController.navigate("enviar_correo") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("✉️ Enviar correo")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { navController.navigate("compartir_contacto") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("📤 Compartir contacto")
        }
    }
}
