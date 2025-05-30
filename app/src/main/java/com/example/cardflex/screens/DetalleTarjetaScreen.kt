package com.example.cardflex.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.cardflex.model.Tarjeta
import com.example.cardflex.util.IdiomaManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

@Composable
fun DetalleTarjetaScreen(navController: NavHostController, tarjetaId: String) {
    val idioma by IdiomaManager.idioma.collectAsState()
    var tarjeta by remember { mutableStateOf<Tarjeta?>(null) }
    var isLoading by remember { mutableStateOf(true) }

    val textos = mapOf(
        "titulo" to mapOf("es" to "Detalle de la Tarjeta", "en" to "Card Details"),
        "regresar" to mapOf("es" to "Regresar", "en" to "Back"),
        "cargando" to mapOf("es" to "Cargando tarjeta...", "en" to "Loading card..."),
        "no_disponible" to mapOf("es" to "No se encontró la tarjeta", "en" to "Card not found")
    )

    // Obtener datos desde Firebase
    LaunchedEffect(tarjetaId) {
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return@LaunchedEffect
        val ref = FirebaseDatabase.getInstance().getReference("tarjetas").child(uid).child(tarjetaId)
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                tarjeta = snapshot.getValue(Tarjeta::class.java)
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
            .padding(24.dp)
    ) {
        Text(
            text = textos["titulo"]?.get(idioma) ?: "",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(24.dp))

        when {
            isLoading -> {
                Text(textos["cargando"]?.get(idioma) ?: "")
            }

            tarjeta != null -> {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFE0F7FA))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("👤 ${tarjeta!!.nombre} ${tarjeta!!.apellido}", fontSize = 18.sp)
                        Text("📞 ${tarjeta!!.telefono}", fontSize = 18.sp)
                        Text("📧 ${tarjeta!!.correo}", fontSize = 18.sp)
                    }
                }
            }

            else -> {
                Text(textos["no_disponible"]?.get(idioma) ?: "")
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text(textos["regresar"]?.get(idioma) ?: "")
        }
    }
}