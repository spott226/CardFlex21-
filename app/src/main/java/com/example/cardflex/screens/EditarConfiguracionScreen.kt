package com.example.cardflex.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.cardflex.FirebaseAuthHelper
import com.example.cardflex.FirebaseHelper
import com.example.cardflex.model.Usuario
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.example.cardflex.util.IdiomaManager
import androidx.compose.runtime.collectAsState

@Composable
fun EditarConfiguracionScreen(navController: NavHostController) {
    val idioma by IdiomaManager.idioma.collectAsState()

    val uid = FirebaseAuth.getInstance().currentUser?.uid

    var nombre by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var ubicacion by remember { mutableStateOf("") }
    var nuevoCorreo by remember { mutableStateOf("") }

    var snackbarMessage by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(true) }

    val textos = mapOf(
        "titulo" to mapOf("es" to "Editar Perfil", "en" to "Edit Profile"),
        "labelNombre" to mapOf("es" to "Nombre del usuario", "en" to "User Name"),
        "labelCorreo" to mapOf("es" to "Correo electrónico", "en" to "Email"),
        "labelUbicacion" to mapOf("es" to "Ubicación", "en" to "Location"),
        "btnGuardar" to mapOf("es" to "Guardar", "en" to "Save"),
        "seccionSeguridad" to mapOf("es" to "Seguridad", "en" to "Security"),
        "btnCambiarContrasena" to mapOf("es" to "Cambiar contraseña", "en" to "Change Password"),
        "labelNuevoCorreo" to mapOf("es" to "Nuevo correo electrónico", "en" to "New Email"),
        "btnActualizarCorreo" to mapOf("es" to "Actualizar correo", "en" to "Update Email"),
        "btnCancelar" to mapOf("es" to "Cancelar", "en" to "Cancel"),
        "msgCargandoDatos" to mapOf("es" to "Error al cargar datos", "en" to "Error loading data"),
        "msgPerfilActualizado" to mapOf("es" to "Perfil actualizado correctamente", "en" to "Profile updated successfully"),
        "msgCompletarCampos" to mapOf("es" to "Completa todos los campos", "en" to "Please fill all fields"),
        "msgCorreoRecuperacionEnviado" to mapOf("es" to "Correo de recuperación enviado", "en" to "Recovery email sent"),
        "msgErrorEnviarCorreo" to mapOf("es" to "Error al enviar correo", "en" to "Error sending email"),
        "msgCorreoActualizado" to mapOf("es" to "Correo actualizado correctamente", "en" to "Email updated successfully"),
        "msgErrorActualizarCorreo" to mapOf("es" to "Error al actualizar correo", "en" to "Error updating email"),
        "msgIntroduceCorreoValido" to mapOf("es" to "Introduce un nuevo correo válido", "en" to "Enter a valid new email")
    )

    // Cargar datos
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
                    snackbarMessage = textos["msgCargandoDatos"]?.get(idioma)
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
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(textos["titulo"]?.get(idioma) ?: "", style = MaterialTheme.typography.titleLarge)

            // Datos de perfil
            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text(textos["labelNombre"]?.get(idioma) ?: "") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = correo,
                onValueChange = { correo = it },
                label = { Text(textos["labelCorreo"]?.get(idioma) ?: "") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = ubicacion,
                onValueChange = { ubicacion = it },
                label = { Text(textos["labelUbicacion"]?.get(idioma) ?: "") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    if (nombre.isNotBlank() && correo.isNotBlank() && ubicacion.isNotBlank() && uid != null) {
                        val usuarioActualizado = Usuario(nombre, correo, ubicacion)
                        FirebaseHelper.actualizarUsuario(uid, usuarioActualizado)
                        snackbarMessage = textos["msgPerfilActualizado"]?.get(idioma)
                        navController.navigate("dashboard")
                    } else {
                        snackbarMessage = textos["msgCompletarCampos"]?.get(idioma)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(30.dp)
            ) {
                Text(textos["btnGuardar"]?.get(idioma) ?: "")
            }

            Divider(thickness = 1.dp)

            // Contraseña
            Text(textos["seccionSeguridad"]?.get(idioma) ?: "", fontSize = 18.sp)
            OutlinedButton(
                onClick = {
                    FirebaseAuthHelper.enviarCorreoRecuperacion { success, error ->
                        snackbarMessage = if (success) {
                            textos["msgCorreoRecuperacionEnviado"]?.get(idioma)
                        } else {
                            error ?: textos["msgErrorEnviarCorreo"]?.get(idioma)
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(30.dp)
            ) {
                Text(textos["btnCambiarContrasena"]?.get(idioma) ?: "")
            }

            // Cambiar correo
            OutlinedTextField(
                value = nuevoCorreo,
                onValueChange = { nuevoCorreo = it },
                label = { Text(textos["labelNuevoCorreo"]?.get(idioma) ?: "") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedButton(
                onClick = {
                    if (nuevoCorreo.isNotBlank()) {
                        FirebaseAuthHelper.actualizarCorreo(nuevoCorreo) { success, error ->
                            snackbarMessage = if (success) {
                                correo = nuevoCorreo
                                textos["msgCorreoActualizado"]?.get(idioma)
                            } else {
                                error ?: textos["msgErrorActualizarCorreo"]?.get(idioma)
                            }
                        }
                    } else {
                        snackbarMessage = textos["msgIntroduceCorreoValido"]?.get(idioma)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(30.dp)
            ) {
                Text(textos["btnActualizarCorreo"]?.get(idioma) ?: "")
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedButton(
                onClick = { navController.navigate("dashboard") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(30.dp)
            ) {
                Text(textos["btnCancelar"]?.get(idioma) ?: "")
            }

            snackbarMessage?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}