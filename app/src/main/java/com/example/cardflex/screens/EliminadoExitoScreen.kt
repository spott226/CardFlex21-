package com.example.cardflex.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
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
fun EliminadoExitoScreen(navController: NavHostController) {
    val idioma by IdiomaManager.idioma.collectAsState()

    val textos = mapOf(
        "descripcion_icono" to mapOf("es" to "Contacto eliminado", "en" to "Contact deleted"),
        "mensaje" to mapOf("es" to "¡Contacto eliminado!", "en" to "Contact deleted!"),
        "volver" to mapOf("es" to "Volver", "en" to "Back")
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
            imageVector = Icons.Default.Delete,
            contentDescription = textos["descripcion_icono"]?.get(idioma) ?: "",
            tint = Color(0xFFF44336),
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
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = MaterialTheme.shapes.medium,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF44336))
        ) {
            Text(textos["volver"]?.get(idioma) ?: "", color = Color.White)
        }
    }
}