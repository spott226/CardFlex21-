package com.example.cardflex.screens

import android.app.Activity
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.cardflex.FirebaseHelper
import com.example.cardflex.model.Usuario
import com.example.cardflex.util.IdiomaManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.launch
import androidx.compose.runtime.collectAsState

@Composable
fun InfoUsuarioScreen(navController: NavHostController) {
    val uid = FirebaseAuth.getInstance().currentUser?.uid
    var usuario by remember { mutableStateOf(Usuario()) }
    var isLoading by remember { mutableStateOf(true) }

    val context = LocalContext.current
    val activity = context as? Activity
    val scope = rememberCoroutineScope()

    // Estado de idioma con reactividad
    val idioma by IdiomaManager.idioma.collectAsState()

    val textos = mapOf(
        "titulo" to mapOf("es" to "Información del usuario", "en" to "User Information"),
        "nombre" to mapOf("es" to "Nombre", "en" to "Name"),
        "correo" to mapOf("es" to "Correo", "en" to "Email"),
        "ubicacion" to mapOf("es" to "Ubicación", "en" to "Location"),
        "regresar" to mapOf("es" to "Regresar", "en" to "Back"),
        "idioma" to mapOf("es" to "Idioma", "en" to "Language"),
        "espanol" to mapOf("es" to "Español", "en" to "Spanish"),
        "ingles" to mapOf("es" to "Inglés", "en" to "English"),
        "cargando" to mapOf("es" to "Cargando...", "en" to "Loading...")
    )

    LaunchedEffect(uid) {
        uid?.let {
            FirebaseHelper.obtenerUsuario(it, object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    usuario = snapshot.getValue(Usuario::class.java) ?: Usuario()
                    isLoading = false
                }

                override fun onCancelled(error: DatabaseError) {
                    isLoading = false
                }
            })
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = textos["titulo"]?.get(idioma) ?: "",
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(modifier = Modifier.height(24.dp))

        if (isLoading) {
            Text(
                "🔄 ${textos["cargando"]?.get(idioma)}",
                fontSize = 18.sp
            )
        } else {
            Text("👤 ${textos["nombre"]?.get(idioma)}: ${usuario.name}", fontSize = 18.sp)
            Text("📧 ${textos["correo"]?.get(idioma)}: ${usuario.designation}", fontSize = 18.sp)
            Text("📍 ${textos["ubicacion"]?.get(idioma)}: ${usuario.location}", fontSize = 18.sp)
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(onClick = { navController.popBackStack() }) {
            Text(textos["regresar"]?.get(idioma) ?: "")
        }

        Spacer(modifier = Modifier.height(24.dp))
        Text(
            "🌐 ${textos["idioma"]?.get(idioma)}: ${
                if (idioma == "es") textos["espanol"]?.get(idioma) else textos["ingles"]?.get(idioma)
            }"
        )

        Row(modifier = Modifier.padding(top = 8.dp)) {
            Button(
                onClick = {
                    scope.launch {
                        IdiomaManager.cambiarIdioma(context, "es")  // Actualiza el idioma global y guarda
                        activity?.recreate()  // Reinicia la actividad para aplicar cambios
                    }
                },
                enabled = idioma != "es",
                modifier = Modifier.padding(end = 8.dp)
            ) {
                Text(textos["espanol"]?.get(idioma) ?: "")
            }
            Button(
                onClick = {
                    scope.launch {
                        IdiomaManager.cambiarIdioma(context, "en")  // Igual para inglés
                        activity?.recreate()
                    }
                },
                enabled = idioma != "en"
            ) {
                Text(textos["ingles"]?.get(idioma) ?: "")
            }
        }
    }
}