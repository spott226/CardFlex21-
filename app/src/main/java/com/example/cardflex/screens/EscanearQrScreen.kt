package com.example.cardflex.screens

import android.Manifest
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import com.example.cardflex.FirebaseHelper
import com.example.cardflex.model.Tarjeta
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun EscanearQrScreen(navController: NavHostController) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }
    val cameraExecutor: ExecutorService = remember { Executors.newSingleThreadExecutor() }

    var hasCameraPermission by remember { mutableStateOf(false) }
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        hasCameraPermission = isGranted
    }

    LaunchedEffect(Unit) {
        requestPermissionLauncher.launch(Manifest.permission.CAMERA)
    }

    val scannerOptions = BarcodeScannerOptions.Builder()
        .setBarcodeFormats(Barcode.FORMAT_QR_CODE)
        .build()
    val scanner = BarcodeScanning.getClient(scannerOptions)

    var scanned by remember { mutableStateOf(false) }
    var previewView by remember { mutableStateOf<PreviewView?>(null) }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Escanea un código QR", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(12.dp))

        AndroidView(
            factory = { context ->
                PreviewView(context).apply {
                    scaleType = PreviewView.ScaleType.FILL_CENTER
                    previewView = this
                }
            },
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
        )

        if (hasCameraPermission && !scanned) {
            cameraProviderFuture.addListener({
                val cameraProvider = cameraProviderFuture.get()
                val preview = Preview.Builder().build().also {
                    it.setSurfaceProvider(previewView?.surfaceProvider)
                }

                val imageAnalyzer = androidx.camera.core.ImageAnalysis.Builder()
                    .build()
                    .also {
                        it.setAnalyzer(cameraExecutor) { imageProxy ->
                            val mediaImage = imageProxy.image
                            if (mediaImage != null && !scanned) {
                                val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
                                scanner.process(image)
                                    .addOnSuccessListener { barcodes ->
                                        for (barcode in barcodes) {
                                            barcode.rawValue?.let { content ->
                                                if (!scanned) {
                                                    scanned = true
                                                    try {
                                                        val parts = content.split("|")
                                                        val tarjeta = Tarjeta(
                                                            nombre = parts.getOrNull(0) ?: "",
                                                            apellido = parts.getOrNull(1) ?: "",
                                                            telefono = parts.getOrNull(2) ?: "",
                                                            correo = parts.getOrNull(3) ?: ""
                                                        )
                                                        FirebaseHelper.guardarTarjeta(
                                                            tarjeta,
                                                            onSuccess = { navController.navigate("escaneo_exitoso") },
                                                            onError = { Log.e("QR", "Error guardando tarjeta: $it") }
                                                        )
                                                    } catch (e: Exception) {
                                                        Log.e("QR", "Error parseando QR: ${e.message}")
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    .addOnFailureListener { Log.e("QR", "Falló el escaneo: ${it.message}") }
                                    .addOnCompleteListener { imageProxy.close() }
                            } else imageProxy.close()
                        }
                    }

                val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    lifecycleOwner,
                    cameraSelector,
                    preview,
                    imageAnalyzer
                )
            }, ContextCompat.getMainExecutor(context))
        } else if (!hasCameraPermission) {
            Text("Permiso de cámara denegado.")
        }
    }
}
