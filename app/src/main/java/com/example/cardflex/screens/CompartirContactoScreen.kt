package com.example.cardflex.screens

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Share
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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.example.cardflex.util.IdiomaManager
import androidx.compose.runtime.collectAsState

@Composable
fun CompartirContactoScreen(navController: NavHostController) {
    val idioma by IdiomaManager.idioma.collectAsState()
    val uid = FirebaseAuth.getInstance().currentUser?.uid
    val dbRef = FirebaseDatabase.getInstance().getReference("contacts").child(uid ?: "")
    var contactos by remember { mutableStateOf(listOf<Contacto>()) }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val lista = mutableListOf<Contacto>()
                for (child in snapshot.children) {
                    val contacto = child.getValue(Contacto::class.java)
                    if (contacto != null) {
                        lista.add(contacto)
                    }
                }
                contactos = lista
            }

            override fun onCancelled(error: DatabaseError) {
                println("Error al cargar contactos: ${error.message}")
            }
        })
    }

    val textos = mapOf(
        "titulo" to mapOf("es" to "COMPARTIR CONTACTO", "en" to "SHARE CONTACT"),
        "volver" to mapOf("es" to "Volver", "en" to "Back"),
        "compartir" to mapOf("es" to "Compartir", "en" to "Share")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121212))
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = textos["volver"]?.get(idioma) ?: "",
                    tint = Color.White
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = textos["titulo"]?.get(idioma) ?: "",
                style = MaterialTheme.typography.titleLarge,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        contactos.forEach { contacto ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
                        .padding(end = 8.dp),
                    tint = Color(0xFF90CAF9)
                )

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "${contacto.nombre} ${contacto.apellido}",
                        fontSize = 16.sp,
                        color = Color.White
                    )
                    Text(
                        text = contacto.correo,
                        fontSize = 12.sp,
                        color = Color.LightGray
                    )
                }

                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = textos["compartir"]?.get(idioma) ?: "",
                    modifier = Modifier
                        .size(28.dp)
                        .clickable {
                            val intent = Intent().apply {
                                action = Intent.ACTION_SEND
                                putExtra(
                                    Intent.EXTRA_TEXT,
                                    "Nombre: ${contacto.nombre} ${contacto.apellido}\n" +
                                            "Teléfono: ${contacto.telefono}\n" +
                                            "Correo: ${contacto.correo}"
                                )
                                type = "text/plain"
                            }
                            val shareIntent = Intent.createChooser(intent, textos["compartir"]?.get(idioma) ?: "")
                            context.startActivity(shareIntent)
                        },
                    tint = Color(0xFFE91E63)
                )
            }
        }
    }
}