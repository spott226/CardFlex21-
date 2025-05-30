package com.example.cardflex.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.example.cardflex.util.IdiomaManager
import androidx.compose.runtime.collectAsState

@Composable
fun ForgotPasswordScreen(navController: NavHostController) {
    val idioma by IdiomaManager.idioma.collectAsState()

    var email by remember { mutableStateOf("") }
    var message by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }

    val textos = mapOf(
        "titulo" to mapOf("es" to "Recuperar Contraseña", "en" to "Forgot Password"),
        "labelEmail" to mapOf("es" to "Introduce tu correo", "en" to "Enter your email"),
        "btnEnviar" to mapOf("es" to "Enviar enlace de recuperación", "en" to "Send recovery link"),
        "msgVolverLogin" to mapOf("es" to "Volver al inicio de sesión", "en" to "Back to login"),
        "msgRecuperacionEnviada" to mapOf("es" to "Correo de recuperación enviado a", "en" to "Recovery email sent to"),
        "msgError" to mapOf("es" to "Error", "en" to "Error")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(textos["titulo"]?.get(idioma) ?: "", fontSize = 28.sp, color = MaterialTheme.colorScheme.primary)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(textos["labelEmail"]?.get(idioma) ?: "") },
            leadingIcon = { Icon(Icons.Default.Email, contentDescription = textos["labelEmail"]?.get(idioma) ?: "") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                isLoading = true
                FirebaseAuth.getInstance()
                    .sendPasswordResetEmail(email)
                    .addOnCompleteListener { task ->
                        isLoading = false
                        message = if (task.isSuccessful) {
                            "${textos["msgRecuperacionEnviada"]?.get(idioma)} $email"
                        } else {
                            "${textos["msgError"]?.get(idioma)}: ${task.exception?.message}"
                        }
                    }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = email.isNotBlank()
        ) {
            Text(textos["btnEnviar"]?.get(idioma) ?: "")
        }

        Spacer(modifier = Modifier.height(16.dp))

        message?.let {
            Text(text = it, color = MaterialTheme.colorScheme.secondary)
        }

        if (isLoading) {
            Spacer(modifier = Modifier.height(8.dp))
            CircularProgressIndicator()
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(onClick = {
            navController.navigate("login")
        }) {
            Text(textos["msgVolverLogin"]?.get(idioma) ?: "")
        }
    }
}