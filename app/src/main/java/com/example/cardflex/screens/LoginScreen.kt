package com.example.cardflex.screens

import android.app.Activity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.cardflex.FirebaseAuthHelper
import com.example.cardflex.util.IdiomaManager
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(navController: NavHostController) {
    val contexto = LocalContext.current
    val activity = contexto as? Activity
    val scope = rememberCoroutineScope()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }

    // Observa el idioma global
    val idioma by IdiomaManager.idioma.collectAsState()

    // Textos dinámicos según idioma
    val textos = mapOf(
        "sign_in" to mapOf("es" to "Iniciar sesión", "en" to "Sign in"),
        "email" to mapOf("es" to "Correo electrónico", "en" to "Email"),
        "password" to mapOf("es" to "Contraseña", "en" to "Password"),
        "forgot_password" to mapOf("es" to "¿Olvidaste tu contraseña?", "en" to "Forgot password?"),
        "no_account" to mapOf("es" to "¿No tienes cuenta? Regístrate", "en" to "Don't have an account? Sign up"),
        "login_button" to mapOf("es" to "Iniciar Sesión", "en" to "Log In")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            textos["sign_in"]?.get(idioma) ?: "Iniciar sesión",
            fontSize = 28.sp,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(textos["email"]?.get(idioma) ?: "Correo electrónico") },
            leadingIcon = { Icon(Icons.Default.Email, contentDescription = "Email icon") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(textos["password"]?.get(idioma) ?: "Contraseña") },
            singleLine = true,
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            leadingIcon = {
                Icon(Icons.Default.Visibility, contentDescription = "Password icon")
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = textos["forgot_password"]?.get(idioma) ?: "¿Olvidaste tu contraseña?",
            color = MaterialTheme.colorScheme.primary,
            fontSize = 14.sp,
            modifier = Modifier
                .align(Alignment.End)
                .clickable {
                    navController.navigate("forgot_password")
                }
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                isLoading = true
                FirebaseAuthHelper.iniciarSesion(email, password) { success, error ->
                    isLoading = false
                    if (success) {
                        navController.navigate("dashboard") {
                            popUpTo("login") { inclusive = true }
                        }
                    } else {
                        errorMessage = error
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text(textos["login_button"]?.get(idioma) ?: "Iniciar Sesión")
        }

        errorMessage?.let {
            Spacer(modifier = Modifier.height(8.dp))
            Text(it, color = MaterialTheme.colorScheme.error)
        }

        if (isLoading) {
            Spacer(modifier = Modifier.height(8.dp))
            CircularProgressIndicator()
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            textos["no_account"]?.get(idioma) ?: "¿No tienes cuenta? Regístrate",
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.clickable {
                navController.navigate("register")
            }
        )
    }
}
