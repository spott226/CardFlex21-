package com.example.cardflex.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.cardflex.FirebaseHelper
import com.example.cardflex.model.Tarjeta
import com.example.cardflex.util.IdiomaManager
import androidx.compose.runtime.collectAsState

@Composable
fun CrearTarjetaScreen(navController: NavHostController) {
    val idioma by IdiomaManager.idioma.collectAsState()

    var nombre by remember { mutableStateOf("") }
    var apellido by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }

    var snackbarMessage by remember { mutableStateOf<String?>(null) }

    val textos = mapOf(
        "titulo" to mapOf("es" to "Crear Tarjeta Digital", "en" to "Create Digital Card"),
        "nombre" to mapOf("es" to "Nombre", "en" to "First Name"),
        "apellido" to mapOf("es" to "Apellido", "en" to "Last Name"),
        "telefono" to mapOf("es" to "Teléfono", "en" to "Phone"),
        "correo" to mapOf("es" to "Correo electrónico", "en" to "Email"),
        "botonGuardar" to mapOf("es" to "Guardar tarjeta", "en" to "Save Card"),
        "camposVacios" to mapOf("es" to "Todos los campos son obligatorios", "en" to "All fields are required")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = textos["titulo"]?.get(idioma) ?: "",
            style = MaterialTheme.typography.headlineSmall,
            fontSize = 22.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text(textos["nombre"]?.get(idioma) ?: "") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = apellido,
            onValueChange = { apellido = it },
            label = { Text(textos["apellido"]?.get(idioma) ?: "") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = telefono,
            onValueChange = { telefono = it },
            label = { Text(textos["telefono"]?.get(idioma) ?: "") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = correo,
            onValueChange = { correo = it },
            label = { Text(textos["correo"]?.get(idioma) ?: "") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                if (nombre.isBlank() || apellido.isBlank() || telefono.isBlank() || correo.isBlank()) {
                    snackbarMessage = textos["camposVacios"]?.get(idioma) ?: "Faltan campos"
                    return@Button
                }

                val tarjeta = Tarjeta(
                    nombre = nombre.trim(),
                    apellido = apellido.trim(),
                    telefono = telefono.trim(),
                    correo = correo.trim()
                )

                FirebaseHelper.guardarTarjeta(
                    tarjeta,
                    onSuccess = {
                        navController.navigate("confirmacion_contacto")
                    },
                    onError = { error ->
                        snackbarMessage = error
                    }
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(textos["botonGuardar"]?.get(idioma) ?: "")
        }

        snackbarMessage?.let {
            Snackbar(
                modifier = Modifier.padding(top = 16.dp),
                action = {
                    TextButton(onClick = { snackbarMessage = null }) {
                        Text("OK")
                    }
                }
            ) {
                Text(it)
            }
        }
    }
}