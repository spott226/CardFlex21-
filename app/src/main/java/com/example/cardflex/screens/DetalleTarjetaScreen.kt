package com.example.cardflex.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun DetalleTarjetaScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text(
            text = "Detalle de la Tarjeta",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFE0F7FA))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("👤 Nombre: Juan Pérez", fontSize = 18.sp)
                Text("🏢 Empresa: Ejemplo S.A.", fontSize = 18.sp)
                Text("📞 Teléfono: 449 123 4567", fontSize = 18.sp)
                Text("📧 Correo: juan.perez@ejemplo.com", fontSize = 18.sp)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Regresar")
        }
    }
}
