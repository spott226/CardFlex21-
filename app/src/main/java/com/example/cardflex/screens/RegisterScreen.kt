package com.example.cardflex.screens

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.cardflex.FirebaseAuthHelper
import com.example.cardflex.FirebaseHelper
import com.example.cardflex.GoogleAuthHelper
import com.example.cardflex.model.Usuario
import com.example.cardflex.util.IdiomaManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import kotlinx.coroutines.launch

@Composable
fun RegisterScreen(navController: NavHostController) {
    val context = LocalContext.current
    val activity = context as? Activity
    val scope = rememberCoroutineScope()

    var name by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var confirmEmail by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var acceptedTerms by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.result
            if (account != null && context is Activity) {
                GoogleAuthHelper.firebaseAuthWithGoogle(account) { success, error ->
                    if (success) {
                        navController.navigate("dashboard") {
                            popUpTo("register") { inclusive = true }
                        }
                    } else {
                        errorMessage = error ?: "Error al iniciar sesión con Google"
                    }
                }
            }
        } catch (e: Exception) {
            errorMessage = e.message ?: "Error inesperado al autenticar"
        }
    }

    val idioma by IdiomaManager.idioma.collectAsState()

    val textos = mapOf(
        "sign_up" to mapOf("es" to "Registrarse", "en" to "Sign up"),
        "name" to mapOf("es" to "Nombre", "en" to "Name"),
        "last_name" to mapOf("es" to "Apellido", "en" to "Last Name"),
        "email" to mapOf("es" to "Correo electrónico", "en" to "Email"),
        "confirm_email" to mapOf("es" to "Confirmar correo", "en" to "Confirm Email"),
        "password" to mapOf("es" to "Contraseña", "en" to "Password"),
        "confirm_password" to mapOf("es" to "Confirmar contraseña", "en" to "Confirm Password"),
        "terms" to mapOf("es" to "Acepto los términos y condiciones", "en" to "I agree with the Terms & Conditions"),
        "error_name" to mapOf("es" to "Por favor completa tu nombre y apellido", "en" to "Please complete your name and last name"),
        "error_email" to mapOf("es" to "Los correos no coinciden", "en" to "Emails do not match"),
        "error_password" to mapOf("es" to "Las contraseñas no coinciden", "en" to "Passwords do not match"),
        "error_terms" to mapOf("es" to "Debes aceptar los términos y condiciones", "en" to "You must accept the terms and conditions"),
        "sign_up_button" to mapOf("es" to "Registrarse", "en" to "Sign up"),
        "google_button" to mapOf("es" to "Continuar con Google", "en" to "Continue with Google"),
        "facebook_button" to mapOf("es" to "Iniciar sesión con Facebook", "en" to "Sign in with Facebook")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = textos["sign_up"]?.get(idioma) ?: "Registrarse",
            fontSize = 28.sp,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text(textos["name"]?.get(idioma) ?: "Nombre") },
            leadingIcon = { Icon(Icons.Default.Person, contentDescription = "Name icon") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = lastName,
            onValueChange = { lastName = it },
            label = { Text(textos["last_name"]?.get(idioma) ?: "Apellido") },
            leadingIcon = { Icon(Icons.Default.Person, contentDescription = "Last name icon") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(textos["email"]?.get(idioma) ?: "Correo electrónico") },
            leadingIcon = { Icon(Icons.Default.Email, contentDescription = "Email icon") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = confirmEmail,
            onValueChange = { confirmEmail = it },
            label = { Text(textos["confirm_email"]?.get(idioma) ?: "Confirmar correo") },
            leadingIcon = { Icon(Icons.Default.Email, contentDescription = "Confirm Email icon") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(textos["password"]?.get(idioma) ?: "Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            leadingIcon = { Icon(Icons.Default.Visibility, contentDescription = "Password icon") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text(textos["confirm_password"]?.get(idioma) ?: "Confirmar contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            leadingIcon = { Icon(Icons.Default.Visibility, contentDescription = "Confirm Password icon") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {
            Checkbox(checked = acceptedTerms, onCheckedChange = { acceptedTerms = it })
            Text(text = textos["terms"]?.get(idioma) ?: "Acepto los términos y condiciones", fontSize = 14.sp)
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

        Button(
            onClick = {
                when {
                    name.isEmpty() || lastName.isEmpty() -> errorMessage = textos["error_name"]?.get(idioma)
                    email != confirmEmail -> errorMessage = textos["error_email"]?.get(idioma)
                    password != confirmPassword -> errorMessage = textos["error_password"]?.get(idioma)
                    !acceptedTerms -> errorMessage = textos["error_terms"]?.get(idioma)
                    else -> {
                        isLoading = true
                        FirebaseAuthHelper.registrarUsuario(email, password) { success, error ->
                            isLoading = false
                            if (success) {
                                val uid = FirebaseAuthHelper.obtenerUsuarioActual()?.uid
                                if (uid != null) {
                                    val usuario = Usuario(
                                        name = "$name $lastName",
                                        designation = email,
                                        location = "Aguascalientes, México"
                                    )
                                    FirebaseHelper.actualizarUsuario(uid, usuario)
                                }

                                navController.navigate("login") {
                                    popUpTo("register") { inclusive = true }
                                }
                            } else {
                                errorMessage = error
                            }
                        }
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text(textos["sign_up_button"]?.get(idioma) ?: "Registrarse")
        }

        Spacer(modifier = Modifier.height(16.dp))
        Divider()
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (context is Activity) {
                    GoogleAuthHelper.initGoogleClient(context)
                    val signInIntent = GoogleAuthHelper.googleSignInClient.signInIntent
                    launcher.launch(signInIntent)
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(textos["google_button"]?.get(idioma) ?: "Continuar con Google", color = Color.White)
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { /* Facebook Sign-In simulado */ },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(textos["facebook_button"]?.get(idioma) ?: "Iniciar sesión con Facebook", color = Color.White)
        }
    }
}