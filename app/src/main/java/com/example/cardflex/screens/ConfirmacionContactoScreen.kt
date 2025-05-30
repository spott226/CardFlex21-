package com.example.cardflex.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.cardflex.util.IdiomaManager
import kotlinx.coroutines.delay

@Composable
fun ConfirmacionContactoScreen(navController: NavHostController) {
    val idioma by IdiomaManager.idioma.collectAsState()

    val textos = mapOf(
        "mensaje" to mapOf("es" to "¡Contacto guardado con éxito!", "en" to "Contact saved successfully!"),
        "descripcion_icono" to mapOf("es" to "Éxito", "en" to "Success")
    )

    // Espera 2 segundos y redirige al dashboard
    LaunchedEffect(Unit) {
        delay(2000)
        navController.navigate("dashboard") {
            popUpTo("confirmacion_contacto") { inclusive = true }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = textos["mensaje"]?.get(idioma) ?: "",
            style = MaterialTheme.typography.titleLarge,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(32.dp))

        Icon(
            imageVector = Icons.Default.CheckCircle,
            contentDescription = textos["descripcion_icono"]?.get(idioma) ?: "",
            modifier = Modifier.size(80.dp),
            tint = Color.Green
        )
    }
}