package com.example.cardflex.screens

import android.util.Log
import android.widget.Toast
import androidx.biometric.BiometricManager
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavHostController
import com.example.cardflex.R
import com.example.cardflex.util.BiometricHelper
import com.example.cardflex.util.IdiomaManager
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavHostController) {
    val idioma by IdiomaManager.idioma.collectAsState()
    val context = LocalContext.current
    val activity = context as FragmentActivity
    var mostrarDialogo by remember { mutableStateOf(false) }

    val helper = remember {
        BiometricHelper(
            context = activity,
            onSuccess = {
                navController.navigate("dashboard") {
                    popUpTo("splash") { inclusive = true }
                }
            },
            onError = { mensaje ->
                Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show()
            }
        )
    }

    LaunchedEffect(idioma) {
        delay(2000)
        val user = try {
            FirebaseAuth.getInstance().currentUser
        } catch (e: Exception) {
            Log.e("SplashScreen", "Error al obtener usuario", e)
            null
        }
        if (user != null) {
            mostrarDialogo = true
        } else {
            navController.navigate("login") {
                popUpTo("splash") { inclusive = true }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = R.drawable.logo_cardflow),
                contentDescription = "Logo CardFlow",
                modifier = Modifier.size(200.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = mapOf("es" to "CardFlow", "en" to "CardFlow")[idioma] ?: "CardFlow",
                style = MaterialTheme.typography.headlineMedium,
                fontSize = 30.sp
            )
        }

        if (mostrarDialogo) {
            AlertDialog(
                onDismissRequest = {},
                title = { Text("Verificación requerida") },
                text = { Text("Ya hay una sesión activa. Verifiquemos tu identidad con tu huella digital.") },
                confirmButton = {
                    TextButton(onClick = {
                        mostrarDialogo = false
                        if (helper.esCompatible()) {
                            helper.iniciarAutenticacion()
                        } else {
                            Toast.makeText(context, "Tu dispositivo no soporta huella", Toast.LENGTH_SHORT).show()
                            navController.navigate("dashboard") {
                                popUpTo("splash") { inclusive = true }
                            }
                        }
                    }) {
                        Text("Verificar")
                    }
                }
            )
        }
    }
}