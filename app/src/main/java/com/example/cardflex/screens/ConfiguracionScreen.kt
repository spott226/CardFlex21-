package com.example.cardflex.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.cardflex.FirebaseHelper
import com.example.cardflex.model.Usuario
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.example.cardflex.util.IdiomaManager
import androidx.compose.runtime.collectAsState

@Composable
fun ConfiguracionScreen(navController: NavHostController) {
    val idioma by IdiomaManager.idioma.collectAsState()

    val uid = FirebaseAuth.getInstance().currentUser?.uid

    var nombre by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var ubicacion by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(true) }

    val textos = mapOf(
        "titulo" to mapOf("es" to "CONFIGURACIÓN DE CONTACTO", "en" to "CONTACT SETTINGS"),
        "labelNombre" to mapOf("es" to "Nombre del usuario", "en" to "User Name"),
        "labelCorreo" to mapOf("es" to "Correo", "en" to "Email"),
        "labelDireccion" to mapOf("es" to "Dirección", "en" to "Address"),
        "btnCerrarSesion" to mapOf("es" to "Cerrar Sesión", "en" to "Log Out"),
        "btnEditarPerfil" to mapOf("es" to "EDITAR PERFIL", "en" to "EDIT PROFILE")
    )

    // Cargar datos del usuario desde Firebase
    LaunchedEffect(uid) {
        if (uid != null) {
            FirebaseHelper.obtenerUsuario(uid, object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val usuario = snapshot.getValue(Usuario::class.java)
                    usuario?.let {
                        nombre = it.name
                        correo = it.designation
                        ubicacion = it.location
                    }
                    isLoading = false
                }

                override fun onCancelled(error: DatabaseError) {
                    isLoading = false
                }
            })
        }
    }

    if (isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = textos["titulo"]?.get(idioma) ?: "",
                tint = Color.Gray,
                modifier = Modifier
                    .size(100.dp)
                    .padding(top = 16.dp, bottom = 8.dp)
            )

            Text(
                text = textos["titulo"]?.get(idioma) ?: "",
                style = MaterialTheme.typography.titleMedium,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            OutlinedTextField(
                value = nombre,
                onValueChange = {},
                readOnly = true,
                label = { Text(textos["labelNombre"]?.get(idioma) ?: "") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = correo,
                onValueChange = {},
                readOnly = true,
                label = { Text(textos["labelCorreo"]?.get(idioma) ?: "") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = ubicacion,
                onValueChange = {},
                readOnly = true,
                label = { Text(textos["labelDireccion"]?.get(idioma) ?: "") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    navController.navigate("login") {
                        popUpTo("dashboard") { inclusive = true }
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(30.dp)
            ) {
                Text(textos["btnCerrarSesion"]?.get(idioma) ?: "", color = Color.White)
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    navController.navigate("editar_configuracion")
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7C4DFF)),
                modifier = Modifier
                    .width(150.dp)
                    .height(50.dp),
                shape = RoundedCornerShape(30.dp)
            ) {
                Text(textos["btnEditarPerfil"]?.get(idioma) ?: "", fontSize = 16.sp, color = Color.White)
            }
        }
    }
}