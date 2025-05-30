package com.example.cardflex.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.cardflex.model.Contacto
import com.example.cardflex.firebase.eliminarContactoPorDatos
import com.example.cardflex.util.IdiomaManager
import androidx.compose.runtime.collectAsState

@Composable
fun AccionContactoScreen(
    navController: NavHostController,
    nombre: String,
    apellido: String,
    telefono: String,
    correo: String
) {
    val context = LocalContext.current
    val idioma by IdiomaManager.idioma.collectAsState()
    var showDialog by remember { mutableStateOf(false) }

    val textos = mapOf(
        "titulo" to mapOf("es" to "CONTACTO", "en" to "CONTACT"),
        "nombre" to mapOf("es" to "Nombre", "en" to "First name"),
        "apellido" to mapOf("es" to "Apellido", "en" to "Last name"),
        "telefono" to mapOf("es" to "Teléfono", "en" to "Phone"),
        "correo" to mapOf("es" to "Correo", "en" to "Email"),
        "eliminar" to mapOf("es" to "¿Eliminar contacto?", "en" to "Delete contact?"),
        "advertencia" to mapOf("es" to "Esta acción no se puede deshacer.", "en" to "This action cannot be undone."),
        "confirmar" to mapOf("es" to "Sí", "en" to "Yes"),
        "cancelar" to mapOf("es" to "Cancelar", "en" to "Cancel")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = textos["titulo"]?.get(idioma) ?: "",
            style = MaterialTheme.typography.titleLarge,
            fontSize = 22.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        Icon(
            imageVector = Icons.Default.Person,
            contentDescription = "Foto",
            modifier = Modifier.size(120.dp),
            tint = Color(0xFF90CAF9)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text("$nombre $apellido", fontSize = 20.sp)

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFB2EBF2), RoundedCornerShape(20.dp))
                .padding(16.dp)
        ) {
            InfoRow(label = textos["nombre"]?.get(idioma) ?: "", value = nombre, icon = Icons.Default.Person)
            InfoRow(label = textos["apellido"]?.get(idioma) ?: "", value = apellido, icon = Icons.Default.Person)
            InfoRow(label = textos["telefono"]?.get(idioma) ?: "", value = telefono, icon = Icons.Default.Call)
            InfoRow(label = textos["correo"]?.get(idioma) ?: "", value = correo, icon = Icons.Default.Email)
        }

        Spacer(modifier = Modifier.height(32.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            ActionIconButton(icon = Icons.Default.Call, color = Color(0xFF4CAF50)) {
                val intent = Intent(Intent.ACTION_DIAL).apply {
                    data = Uri.parse("tel:$telefono")
                }
                context.startActivity(intent)
            }

            ActionIconButton(icon = Icons.Default.Email, color = Color(0xFF2196F3)) {
                navController.navigate("enviar_correo/$correo")
            }

            ActionIconButton(icon = Icons.Default.Share, color = Color(0xFF9C27B0)) {
                navController.navigate("compartir_contacto")
            }

            ActionIconButton(icon = Icons.Default.Delete, color = Color(0xFFF44336)) {
                showDialog = true
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(textos["eliminar"]?.get(idioma) ?: "") },
            text = { Text(textos["advertencia"]?.get(idioma) ?: "") },
            confirmButton = {
                TextButton(onClick = {
                    showDialog = false
                    val contacto = Contacto(nombre, apellido, telefono, correo)
                    eliminarContactoPorDatos(
                        contacto = contacto,
                        onSuccess = {
                            navController.navigate("eliminado_exito") {
                                popUpTo("dashboard") { inclusive = false }
                            }
                        },
                        onFailure = {
                            println("Error al eliminar: ${it.message}")
                        }
                    )
                }) {
                    Text(textos["confirmar"]?.get(idioma) ?: "", color = Color.Red)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text(textos["cancelar"]?.get(idioma) ?: "")
                }
            }
        )
    }
}

@Composable
fun InfoRow(label: String, value: String, icon: androidx.compose.ui.graphics.vector.ImageVector) {
    OutlinedTextField(
        value = value,
        onValueChange = {},
        label = { Text(label) },
        leadingIcon = {
            Icon(imageVector = icon, contentDescription = null)
        },
        enabled = false,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    )
}

@Composable
fun ActionIconButton(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    color: Color,
    onClick: () -> Unit
) {
    IconButton(
        onClick = onClick,
        modifier = Modifier
            .size(60.dp)
            .background(color, shape = CircleShape)
    ) {
        Icon(imageVector = icon, contentDescription = null, tint = Color.White)
    }
}