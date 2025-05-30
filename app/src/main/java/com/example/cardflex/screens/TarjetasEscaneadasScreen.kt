package com.example.cardflex.screens

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.navigation.NavHostController
import com.example.cardflex.model.Tarjeta
import com.example.cardflex.util.IdiomaManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.Paragraph
import java.io.File
import java.io.OutputStream

@Composable
fun TarjetasEscaneadasScreen(navController: NavHostController) {
    val idioma by IdiomaManager.idioma.collectAsState()
    val context = LocalContext.current

    val textos = mapOf(
        "titulo" to mapOf("es" to "Tarjetas Escaneadas", "en" to "Scanned Cards"),
        "nombre" to mapOf("es" to "Nombre", "en" to "Name"),
        "correo" to mapOf("es" to "Correo", "en" to "Email"),
        "telefono" to mapOf("es" to "Teléfono", "en" to "Phone"),
        "pdf" to mapOf("es" to "Descargar PDF", "en" to "Download PDF"),
        "xml" to mapOf("es" to "Descargar XML", "en" to "Download XML")
    )

    var tarjetas by remember { mutableStateOf<List<Tarjeta>>(emptyList()) }
    var ids by remember { mutableStateOf<List<String>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return@LaunchedEffect
        val ref = FirebaseDatabase.getInstance().getReference("tarjetas").child(uid)
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val lista = mutableListOf<Tarjeta>()
                val idLista = mutableListOf<String>()
                for (child in snapshot.children) {
                    val tarjeta = child.getValue(Tarjeta::class.java)
                    tarjeta?.let {
                        lista.add(it)
                        idLista.add(child.key!!)
                    }
                }
                tarjetas = lista
                ids = idLista
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

        Spacer(modifier = Modifier.height(16.dp))

        if (isLoading) {
            Text("Cargando...")
        } else {
            tarjetas.forEachIndexed { index, tarjeta ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .clickable {
                            navController.navigate("detalle_tarjeta/${ids[index]}")
                        }
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("${textos["nombre"]?.get(idioma)}: ${tarjeta.nombre} ${tarjeta.apellido}")
                        Text("${textos["correo"]?.get(idioma)}: ${tarjeta.correo}")
                        Text("${textos["telefono"]?.get(idioma)}: ${tarjeta.telefono}")
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Button(modifier = Modifier.weight(1f), onClick = {
                exportarTarjetasAPdf(context, tarjetas)
            }) {
                Text(textos["pdf"]?.get(idioma) ?: "")
            }

            Button(modifier = Modifier.weight(1f), onClick = {
                exportarTarjetasAXml(context, tarjetas)
            }) {
                Text(textos["xml"]?.get(idioma) ?: "")
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Button(modifier = Modifier.weight(1f), onClick = {
                compartirArchivo(context, "tarjetas_exportadas.pdf", "application/pdf")
            }) {
                Text("Compartir PDF")
            }

            Button(modifier = Modifier.weight(1f), onClick = {
                compartirArchivo(context, "tarjetas_exportadas.xml", "text/xml")
            }) {
                Text("Compartir XML")
            }
        }
    }
}

fun exportarTarjetasAPdf(context: Context, tarjetas: List<Tarjeta>) {
    try {
        val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val file = File(downloadsDir, "tarjetas_exportadas.pdf")
        val pdfWriter = PdfWriter(file)
        val pdfDocument = PdfDocument(pdfWriter)
        val document = Document(pdfDocument)

        document.add(Paragraph("Tarjetas Digitales"))
        document.add(Paragraph("--------------------------"))

        tarjetas.forEach {
            document.add(Paragraph("Nombre: ${it.nombre} ${it.apellido}"))
            document.add(Paragraph("Teléfono: ${it.telefono}"))
            document.add(Paragraph("Correo: ${it.correo}"))
            document.add(Paragraph(""))
        }

        document.close()
        Toast.makeText(context, "PDF exportado en Downloads", Toast.LENGTH_SHORT).show()
    } catch (e: Exception) {
        Log.e("EXPORT", "Error al exportar PDF: ${e.message}")
    }
}

fun exportarTarjetasAXml(context: Context, tarjetas: List<Tarjeta>) {
    try {
        val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val file = File(downloadsDir, "tarjetas_exportadas.xml")
        file.printWriter().use { writer ->
            writer.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
            writer.println("<tarjetas>")
            tarjetas.forEach {
                writer.println("  <tarjeta>")
                writer.println("    <nombre>${it.nombre}</nombre>")
                writer.println("    <apellido>${it.apellido}</apellido>")
                writer.println("    <telefono>${it.telefono}</telefono>")
                writer.println("    <correo>${it.correo}</correo>")
                writer.println("  </tarjeta>")
            }
            writer.println("</tarjetas>")
        }
        Toast.makeText(context, "XML exportado en Downloads", Toast.LENGTH_SHORT).show()
    } catch (e: Exception) {
        Log.e("EXPORT", "Error al exportar XML: ${e.message}")
    }
}

fun compartirArchivo(context: Context, fileName: String, mimeType: String) {
    try {
        val file = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fileName)
        if (!file.exists()) {
            Toast.makeText(context, "Archivo no encontrado", Toast.LENGTH_SHORT).show()
            return
        }

        val uri: Uri = FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            file
        )

        val intent = Intent(Intent.ACTION_SEND).apply {
            type = mimeType
            putExtra(Intent.EXTRA_STREAM, uri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }

        context.startActivity(Intent.createChooser(intent, "Compartir archivo con..."))
    } catch (e: Exception) {
        Toast.makeText(context, "Error al compartir: ${e.message}", Toast.LENGTH_SHORT).show()
    }
}