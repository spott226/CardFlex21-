package com.example.cardflex.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.cardflex.util.IdiomaManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import androidx.compose.runtime.collectAsState

fun obtenerUltimaTarjetaId(onResult: (String?) -> Unit) {
    val uid = FirebaseAuth.getInstance().currentUser?.uid
    if (uid == null) {
        onResult(null)
        return
    }

    val ref = FirebaseDatabase.getInstance().getReference("tarjetas").child(uid)
    ref.limitToLast(1).addListenerForSingleValueEvent(object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            val tarjetaId = snapshot.children.firstOrNull()?.key
            onResult(tarjetaId)
        }

        override fun onCancelled(error: DatabaseError) {
            onResult(null)
        }
    })
}

@Composable
fun DashboardScreen(navController: NavHostController) {
    val idioma by IdiomaManager.idioma.collectAsState()
    var selectedTab by remember { mutableStateOf("contactos") }

    val textos = mapOf(
        "buscar" to mapOf("es" to "Buscar", "en" to "Search"),
        "perfil" to mapOf("es" to "Perfil", "en" to "Profile"),
        "contactos" to mapOf("es" to "CONTACTOS", "en" to "CONTACTS"),
        "escanear_tarjeta" to mapOf("es" to "ESCANEAR TARJETA", "en" to "SCAN CARD"),
        "crear_contacto" to mapOf("es" to "Crear contacto", "en" to "Create contact"),
        "detalle_contacto" to mapOf("es" to "Detalle de contacto", "en" to "Contact details"),
        "configuracion" to mapOf("es" to "Configuración", "en" to "Settings"),
        "crear_tarjeta" to mapOf("es" to "Crear tarjeta Digital", "en" to "Create Digital Card"),
        "detalle_tarjeta" to mapOf("es" to "Detalle de Tarjetas", "en" to "Card Details"),
        "importar_tarjetas" to mapOf("es" to "Importar Tarjetas", "en" to "Import Cards"),
        "ir" to mapOf("es" to "Ir", "en" to "Go")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(Color(0xFFF1F1F1), RoundedCornerShape(16.dp))
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.Search, contentDescription = textos["buscar"]?.get(idioma), tint = Color.Gray)
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = textos["buscar"]?.get(idioma) ?: "",
                color = Color.Gray,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = textos["perfil"]?.get(idioma),
                tint = Color.Gray,
                modifier = Modifier
                    .size(28.dp)
                    .clip(CircleShape)
                    .clickable {
                        navController.navigate("info_usuario")
                    }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF1F1F1), RoundedCornerShape(20.dp))
                .padding(4.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TabButton(textos["contactos"]?.get(idioma) ?: "", selectedTab == "contactos") {
                selectedTab = "contactos"
            }
            TabButton(textos["escanear_tarjeta"]?.get(idioma) ?: "", selectedTab == "escanear") {
                selectedTab = "escanear"
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        if (selectedTab == "contactos") {
            DashboardOption(textos["crear_contacto"]?.get(idioma) ?: "", Color(0xFF9C27B0), textos["ir"]?.get(idioma) ?: "Ir") {
                navController.navigate("crear_contacto")
            }
            DashboardOption(textos["detalle_contacto"]?.get(idioma) ?: "", Color(0xFFE91E63), textos["ir"]?.get(idioma) ?: "Ir") {
                navController.navigate("contacto_detalle")
            }
            DashboardOption(textos["configuracion"]?.get(idioma) ?: "", Color(0xFFFF9800), textos["ir"]?.get(idioma) ?: "Ir") {
                navController.navigate("configuracion")
            }
        } else {
            DashboardOption(textos["crear_tarjeta"]?.get(idioma) ?: "", Color(0xFF9C27B0), textos["ir"]?.get(idioma) ?: "Ir") {
                navController.navigate("crear_tarjeta")
            }
            DashboardOption(textos["detalle_tarjeta"]?.get(idioma) ?: "", Color(0xFFE91E63), textos["ir"]?.get(idioma) ?: "Ir") {
                obtenerUltimaTarjetaId { id ->
                    if (id != null) {
                        navController.navigate("detalle_tarjeta/$id")
                    } else {
                        println("⚠️ No se encontró ninguna tarjeta para este usuario.")
                    }
                }
            }
            DashboardOption(textos["importar_tarjetas"]?.get(idioma) ?: "", Color(0xFF4CAF50), textos["ir"]?.get(idioma) ?: "Ir") {
                navController.navigate("tarjetas_escaneadas")
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                IconButton(
                    onClick = { navController.navigate("escanear_qr") },
                    modifier = Modifier
                        .weight(1f)
                        .padding(4.dp)
                        .background(Color.White, RoundedCornerShape(8.dp))
                        .height(60.dp)
                ) {
                    Text("📷", fontSize = 32.sp)
                }

                IconButton(
                    onClick = { navController.navigate("galeria_qr") },
                    modifier = Modifier
                        .weight(1f)
                        .padding(4.dp)
                        .background(Color(0xFF3F51B5), RoundedCornerShape(8.dp))
                        .height(60.dp)
                ) {
                    Text("🖼️", fontSize = 32.sp)
                }
            }
        }
    }
}

@Composable
fun TabButton(text: String, selected: Boolean, onClick: () -> Unit) {
    val background = if (selected) Color.White else Color.Transparent

    Box(
        modifier = Modifier
            .height(40.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(background)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = Color.Black,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun DashboardOption(
    text: String,
    iconColor: Color,
    goText: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(iconColor),
            contentAlignment = Alignment.Center
        ) {
            Text("📎", fontSize = 18.sp)
        }

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = text,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.weight(1f)
        )

        Icon(
            Icons.Default.ArrowForwardIos,
            contentDescription = goText,
            tint = Color.Gray
        )
    }
}
