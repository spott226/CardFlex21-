package com.example.cardflex.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.cardflex.model.Contacto
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.example.cardflex.util.IdiomaManager
import androidx.compose.runtime.collectAsState

@Composable
fun ContactoDetalleScreen(navController: NavHostController) {
    val idioma by IdiomaManager.idioma.collectAsState()
    val contactos = remember { mutableStateListOf<Contacto>() }

    val uid = FirebaseAuth.getInstance().currentUser?.uid.orEmpty()
    val dbRef = FirebaseDatabase.getInstance().getReference("contacts").child(uid)

    // Leer desde Firebase
    LaunchedEffect(Unit) {
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                contactos.clear()
                for (child in snapshot.children) {
                    val contacto = child.getValue(Contacto::class.java)
                    contacto?.let { contactos.add(it) }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                println("Error al leer contactos: ${error.message}")
            }
        })
    }

    val textos = mapOf(
        "titulo" to mapOf("es" to "CONTACTOS", "en" to "CONTACTS"),
        "buscar" to mapOf("es" to "Buscar", "en" to "Search"),
        "avatar" to mapOf("es" to "Avatar", "en" to "Avatar"),
        "irAcciones" to mapOf("es" to "Ir a acciones", "en" to "Go to actions")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = textos["titulo"]?.get(idioma) ?: "",
            style = MaterialTheme.typography.titleLarge,
            fontSize = 22.sp,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = "",
            onValueChange = {},
            placeholder = { Text(textos["buscar"]?.get(idioma) ?: "") },
            leadingIcon = {
                Icon(Icons.Default.Person, contentDescription = textos["buscar"]?.get(idioma) ?: "")
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        contactos.forEach { contacto ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = textos["avatar"]?.get(idioma) ?: "",
                    modifier = Modifier
                        .size(40.dp)
                        .padding(end = 8.dp),
                    tint = Color(0xFF90CAF9)
                )

                Column(modifier = Modifier.weight(1f)) {
                    Text(text = "${contacto.nombre} ${contacto.apellido}", fontSize = 16.sp)
                    Text(text = contacto.correo, fontSize = 12.sp, color = Color.Gray)
                }

                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = textos["irAcciones"]?.get(idioma) ?: "",
                    modifier = Modifier
                        .size(30.dp)
                        .clickable {
                            navController.navigate(
                                "acciones_contacto/${contacto.nombre}/${contacto.apellido}/${contacto.telefono}/${contacto.correo}"
                            )
                        },
                    tint = Color.Black
                )
            }
        }
    }
}