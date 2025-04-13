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

@Composable
fun DashboardScreen(navController: NavHostController) {
    var selectedTab by remember { mutableStateOf("contactos") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Barra de búsqueda con ícono y avatar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(Color(0xFFF1F1F1), RoundedCornerShape(16.dp))
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.Search, contentDescription = "Buscar", tint = Color.Gray)
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Buscar",
                color = Color.Gray,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Perfil",
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

        // Tabs
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF1F1F1), RoundedCornerShape(20.dp))
                .padding(4.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TabButton("CONTACTOS", selectedTab == "contactos") {
                selectedTab = "contactos"
            }
            TabButton("ESCANEAR TARJETA", selectedTab == "escanear") {
                selectedTab = "escanear"
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Contenido dinámico
        if (selectedTab == "contactos") {
            DashboardOption("Crear contacto", Color(0xFF9C27B0)) {
                navController.navigate("crear_contacto")
            }
            DashboardOption("Detalle de contacto", Color(0xFFE91E63)) {
                navController.navigate("contacto_detalle")
            }
            DashboardOption("Configuración", Color(0xFFFF9800)) {
                navController.navigate("configuracion")
            }
        } else {
            DashboardOption("Crear tarjeta Digital", Color(0xFF9C27B0)) {
                navController.navigate("crear_tarjeta")
            }
            DashboardOption("Detalle de Tarjetas", Color(0xFFE91E63)) {
                navController.navigate("detalle_tarjeta")
            }
            DashboardOption("Importar Tarjetas", Color(0xFF4CAF50)) {

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
fun DashboardOption(text: String, iconColor: Color, onClick: () -> Unit) {
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

        Icon(Icons.Default.ArrowForwardIos, contentDescription = "Ir", tint = Color.Gray)
    }
}
