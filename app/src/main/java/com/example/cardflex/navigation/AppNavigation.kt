package com.example.cardflex.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.cardflex.screens.*

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController, startDestination = "dashboard") {
        composable("dashboard") { DashboardScreen(navController) }
        composable("crear_contacto") { CrearContactoScreen(navController) }
        composable("confirmacion_contacto") { ConfirmacionContactoScreen(navController) }
        composable("contacto_detalle") { ContactoDetalleScreen(navController) }
        composable("llamada_simulada") { LlamadaSimuladaScreen(navController) }
        composable("escanear_qr") { EscanearQrScreen(navController) }
        composable("galeria_qr") { GaleriaQrScreen(navController) }
        composable("escaneo_exitoso") { EscaneoExitosoScreen(navController) }
        composable("tarjetas_escaneadas") { TarjetasEscaneadasScreen(navController) }
        composable("detalle_tarjeta") { DetalleTarjetaScreen(navController) }
        composable("crear_tarjeta") { CrearTarjetaScreen(navController) }
        composable("compartir_contacto") { CompartirContactoScreen(navController) }
        composable("info_usuario") { InfoUsuarioScreen(navController) }
        composable("descargar_formato") { DescargarFormatoScreen(navController) }
        composable("enviar_correo") { EnviarCorreoScreen(navController) }
        composable("configuracion") { ConfiguracionScreen(navController) }
        }

    }
