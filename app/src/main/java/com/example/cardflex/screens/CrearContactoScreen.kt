package com.example.cardflex.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.cardflex.model.Contacto
import com.example.cardflex.firebase.guardarContacto
import com.example.cardflex.util.IdiomaManager
import androidx.compose.runtime.collectAsState

@Composable
fun CrearContactoScreen(navController: NavHostController) {
    val idioma by IdiomaManager.idioma.collectAsState()

    var nombre by remember { mutableStateOf("") }
    var apellido by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }

    val textos = mapOf(
        "titulo" to mapOf("es" to "CREAR CONTACTO", "en" to "CREATE CONTACT"),
        "agregarFoto" to mapOf("es" to "Agregar foto", "en" to "Add photo"),
        "nombre" to mapOf("es" to "Nombre", "en" to "First Name"),
        "apellido" to mapOf("es" to "Apellido", "en" to "Last Name"),
        "telefono" to mapOf("es" to "Teléfono", "en" to "Phone"),
        "correo" to mapOf("es" to "Correo", "en" to "Email"),
        "guardar" to mapOf("es" to "GUARDAR", "en" to "SAVE"),
        "cancelar" to mapOf("es" to "CANCELAR", "en" to "CANCEL"),
        "errorCampos" to mapOf("es" to "Todos los campos son obligatorios", "en" to "All fields are required")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = textos["titulo"]?.get(idioma) ?: "",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = textos["agregarFoto"]?.get(idioma) ?: "",
                tint = Color.Black,
                modifier = Modifier.size(48.dp)
            )
        }

        Text(textos["agregarFoto"]?.get(idioma) ?: "", fontSize = 14.sp, modifier = Modifier.padding(top = 8.dp))

        Spacer(modifier = Modifier.height(24.dp))

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
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Phone)
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = correo,
            onValueChange = { correo = it },
            label = { Text(textos["correo"]?.get(idioma) ?: "") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email)
        )

        Spacer(modifier = Modifier.height(32.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            OutlinedButton(
                onClick = {
                    if (nombre.isBlank() || apellido.isBlank() || telefono.isBlank() || correo.isBlank()) {
                        println(textos["errorCampos"]?.get(idioma) ?: "")
                        return@OutlinedButton
                    }

                    val contacto = Contacto(nombre, apellido, telefono, correo)

                    guardarContacto(
                        contacto = contacto,
                        onSuccess = {
                            navController.navigate("confirmacion_contacto") {
                                popUpTo("crearContacto") { inclusive = true }
                            }
                        },
                        onFailure = {
                            println("Error al guardar: ${it.message}")
                        }
                    )
                },
                modifier = Modifier.weight(1f).padding(end = 8.dp)
            ) {
                Text(textos["guardar"]?.get(idioma) ?: "")
            }

            Button(
                onClick = {
                    navController.navigate("dashboard")
                },
                modifier = Modifier.weight(1f).padding(start = 8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text(textos["cancelar"]?.get(idioma) ?: "", color = Color.White)
            }
        }
    }
}