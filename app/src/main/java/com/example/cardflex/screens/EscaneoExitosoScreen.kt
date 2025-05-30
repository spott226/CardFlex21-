package com.example.cardflex.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.cardflex.util.IdiomaManager
import kotlinx.coroutines.delay

@Composable
fun EscaneoExitosoScreen(navController: NavHostController) {
    val idioma by IdiomaManager.idioma.collectAsState()

    val textos = mapOf(
        "titulo" to mapOf("es" to "✅ Escaneo Exitoso", "en" to "✅ Scan Successful"),
        "btnVerTarjetas" to mapOf("es" to "Ver tarjetas escaneadas", "en" to "View scanned cards")
    )

    // Redirige automáticamente al dashboard después de 3 segundos
    LaunchedEffect(Unit) {
        delay(3000)
        navController.navigate("dashboard") {
            popUpTo("escanear_qr") { inclusive = true }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF4CAF50)),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = textos["titulo"]?.get(idioma) ?: "",
                color = Color.White,
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = { navController.navigate("tarjetas_escaneadas") },
                colors = ButtonDefaults.buttonColors(containerColor = Color.White)
            ) {
                Text(textos["btnVerTarjetas"]?.get(idioma) ?: "", color = Color.Black)
            }
        }
    }
}