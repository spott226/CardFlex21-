package com.example.cardflex.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.cardflex.util.IdiomaManager
import androidx.compose.runtime.collectAsState

@Composable
fun EnviarCorreoScreen(navController: NavHostController, correo: String) {
    val idioma by IdiomaManager.idioma.collectAsState()

    var mensaje by remember { mutableStateOf("") }
    val para = correo
    val desde = "uses88767@gmail.com"

    val textos = mapOf(
        "titulo" to mapOf("es" to "CORREO ELECTRÓNICO", "en" to "EMAIL"),
        "atras" to mapOf("es" to "Atrás", "en" to "Back"),
        "iconoUsuario" to mapOf("es" to "Icono usuario", "en" to "User icon"),
        "enviandoComo" to mapOf("es" to "Enviando como:", "en" to "Sending as:"),
        "para" to mapOf("es" to "Para:", "en" to "To:"),
        "mensaje" to mapOf("es" to "Mensaje:", "en" to "Message:"),
        "placeholderMensaje" to mapOf("es" to "Escribe tu mensaje aquí...", "en" to "Write your message here..."),
        "btnEnviar" to mapOf("es" to "ENVIAR CORREO", "en" to "SEND EMAIL")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = textos["atras"]?.get(idioma) ?: ""
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(textos["titulo"]?.get(idioma) ?: "", style = MaterialTheme.typography.titleLarge)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Icon(
            imageVector = Icons.Default.Person,
            contentDescription = textos["iconoUsuario"]?.get(idioma) ?: "",
            modifier = Modifier.size(100.dp),
            tint = Color(0xFF90CAF9)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text("${textos["enviandoComo"]?.get(idioma) ?: ""} $desde", color = Color.Gray)

        Spacer(modifier = Modifier.height(24.dp))

        Text(textos["para"]?.get(idioma) ?: "", color = Color.Gray)
        OutlinedTextField(
            value = para,
            onValueChange = {},
            enabled = false,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(textos["mensaje"]?.get(idioma) ?: "", color = Color.Gray)
        OutlinedTextField(
            value = mensaje,
            onValueChange = { mensaje = it },
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
            placeholder = { Text(textos["placeholderMensaje"]?.get(idioma) ?: "") }
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { navController.navigate("correo_enviado_exito") },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF81D4FA)),
            shape = MaterialTheme.shapes.large
        ) {
            Text(textos["btnEnviar"]?.get(idioma) ?: "")
        }
    }
}