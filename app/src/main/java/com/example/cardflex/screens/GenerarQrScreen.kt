package com.example.cardflex.screens

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.cardflex.model.Tarjeta
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter

@Composable
fun GenerarQrScreen(navController: NavHostController, tarjetaId: String) {
    var tarjeta by remember { mutableStateOf<Tarjeta?>(null) }

    // Obtener la tarjeta desde Firebase
    LaunchedEffect(tarjetaId) {
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return@LaunchedEffect
        val ref = FirebaseDatabase.getInstance().getReference("tarjetas").child(uid).child(tarjetaId)
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                tarjeta = snapshot.getValue(Tarjeta::class.java)
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Código QR de la Tarjeta", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(16.dp))

        tarjeta?.let {
            val contenidoQR = "${it.nombre} ${it.apellido}\nTel: ${it.telefono}\nEmail: ${it.correo}"
            val bitmap = generarQr(contenidoQR)

            bitmap?.let { bmp ->
                Image(
                    bitmap = bmp.asImageBitmap(),
                    contentDescription = "QR Code",
                    modifier = Modifier.size(240.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
        } ?: Text("Cargando tarjeta...")

        Button(onClick = { navController.popBackStack() }) {
            Text("Regresar")
        }
    }
}

fun generarQr(text: String): Bitmap? {
    return try {
        val writer = QRCodeWriter()
        val bitMatrix = writer.encode(text, BarcodeFormat.QR_CODE, 512, 512)
        val width = bitMatrix.width
        val height = bitMatrix.height
        val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
        for (x in 0 until width) {
            for (y in 0 until height) {
                bmp.setPixel(x, y, if (bitMatrix[x, y]) android.graphics.Color.BLACK else android.graphics.Color.WHITE)
            }
        }
        bmp
    } catch (e: Exception) {
        null
    }
}