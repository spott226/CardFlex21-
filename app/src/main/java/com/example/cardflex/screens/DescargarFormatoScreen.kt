package com.example.cardflex.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.cardflex.util.IdiomaManager
import androidx.compose.runtime.collectAsState

@Composable
fun DescargarFormatoScreen(navController: NavHostController) {
    val idioma by IdiomaManager.idioma.collectAsState()
    var formatoSeleccionado by remember { mutableStateOf("") }

    val textos = mapOf(
        "titulo" to mapOf("es" to "Selecciona el formato de descarga", "en" to "Select download format"),
        "pdf" to mapOf("es" to "📄 Descargar como PDF", "en" to "📄 Download as PDF"),
        "xml" to mapOf("es" to "📁 Descargar como XML", "en" to "📁 Download as XML"),
        "csv" to mapOf("es" to "📊 Descargar como CSV", "en" to "📊 Download as CSV"),
        "generar" to mapOf("es" to "✅ Se generará archivo en formato:", "en" to "✅ File will be generated in format:"),
        "volver" to mapOf("es" to "Volver", "en" to "Back")
    )

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

        Button(
            onClick = { formatoSeleccionado = "PDF" },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(textos["pdf"]?.get(idioma) ?: "")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { formatoSeleccionado = "XML" },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(textos["xml"]?.get(idioma) ?: "")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { formatoSeleccionado = "CSV" },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(textos["csv"]?.get(idioma) ?: "")
        }

        if (formatoSeleccionado.isNotEmpty()) {
            Spacer(modifier = Modifier.height(32.dp))
            Text("${textos["generar"]?.get(idioma) ?: ""} $formatoSeleccionado")
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(onClick = { navController.popBackStack() }) {
            Text(textos["volver"]?.get(idioma) ?: "")
        }
    }
}