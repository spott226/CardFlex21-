package com.example.cardflex.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.cardflex.FirebaseHelper
import com.example.cardflex.model.Tarjeta
import com.example.cardflex.util.IdiomaManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

@Composable
fun GaleriaQrScreen(navController: NavHostController) {
    val idioma by IdiomaManager.idioma.collectAsState()
    val textos = mapOf(
        "titulo" to mapOf("es" to "Galería de QR registrados", "en" to "Registered QR Gallery"),
        "nombreEjemplo" to mapOf("es" to "Nombre de ejemplo", "en" to "Sample Name"),
        "iconoQr" to mapOf("es" to "Icono QR", "en" to "QR Icon")
    )

    var tarjetas by remember { mutableStateOf<List<Pair<String, Tarjeta>>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return@LaunchedEffect
        FirebaseHelper.obtenerTarjetas(uid, object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val lista = mutableListOf<Pair<String, Tarjeta>>()
                for (child in snapshot.children) {
                    val tarjeta = child.getValue(Tarjeta::class.java)
                    val key = child.key ?: continue
                    if (tarjeta != null) {
                        lista.add(key to tarjeta)
                    }
                }
                tarjetas = lista
                isLoading = false
            }

            override fun onCancelled(error: DatabaseError) {
                isLoading = false
            }
        })
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = textos["titulo"]?.get(idioma) ?: "",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(24.dp))

        if (isLoading) {
            CircularProgressIndicator()
        } else if (tarjetas.isEmpty()) {
            Text("No hay tarjetas escaneadas.")
        } else {
            tarjetas.forEach { (id, tarjeta) ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .clickable {
                            navController.navigate("detalle_tarjeta/$id")
                        }
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.QrCode,
                            contentDescription = textos["iconoQr"]?.get(idioma) ?: "",
                            modifier = Modifier.size(32.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text("${tarjeta.nombre} ${tarjeta.apellido}", style = MaterialTheme.typography.titleMedium)
                            Text(tarjeta.correo, style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }
            }
        }
    }
}