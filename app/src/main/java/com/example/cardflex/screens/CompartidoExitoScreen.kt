package com.example.cardflex.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.cardflex.util.IdiomaManager

@Composable
fun CompartidoExitoScreen(navController: NavHostController) {
    val idioma by IdiomaManager.idioma.collectAsState()

    val textos = mapOf(
        "mensaje" to mapOf("es" to "¡Contacto compartido con éxito!", "en" to "Contact shared successfully!"),
        "aceptar" to mapOf("es" to "Aceptar", "en" to "Accept"),
        "descripcion_icono" to mapOf("es" to "Compartido", "en" to "Shared")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Default.Share,
            contentDescription = textos["descripcion_icono"]?.get(idioma) ?: "",
            tint = Color(0xFFE91E63),
            modifier = Modifier.size(100.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = textos["mensaje"]?.get(idioma) ?: "",
            fontSize = 20.sp,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { navController.popBackStack() },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE91E63))
        ) {
            Text(textos["aceptar"]?.get(idioma) ?: "", color = Color.White)
        }
    }
}