package com.example.cardflex.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.cardflex.screens.*

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController, startDestination = "splash") {
        composable("splash") { SplashScreen(navController) }
        composable("login") { LoginScreen(navController) }
        composable("register") { RegisterScreen(navController) }

        // Ruta dinámica con decodificación segura
        composable(
            "acciones_contacto/{nombre}/{apellido}/{telefono}/{correo}"
        ) { backStackEntry ->
            val nombre = Uri.decode(backStackEntry.arguments?.getString("nombre") ?: "")
            val apellido = Uri.decode(backStackEntry.arguments?.getString("apellido") ?: "")
            val telefono = Uri.decode(backStackEntry.arguments?.getString("telefono") ?: "")
            val correo = Uri.decode(backStackEntry.arguments?.getString("correo") ?: "")

            AccionContactoScreen(
                navController = navController,
                nombre = nombre,
                apellido = apellido,
                telefono = telefono,
                correo = correo
            )
        }

        // Otras rutas normales sin cambios...
        composable("editar_configuracion") { EditarConfiguracionScreen(navController) }
        composable("forgot_password") { ForgotPasswordScreen(navController) }
        composable("correo_enviado_exito") { CorreoEnviadoExitoScreen(navController) }
        composable("dashboard") { DashboardScreen(navController) }
        composable("compartido_exito") { CompartidoExitoScreen(navController) }
        composable("crear_contacto") { CrearContactoScreen(navController) }
        composable("eliminado_exito") { EliminadoExitoScreen(navController) }
        composable("confirmacion_contacto") { ConfirmacionContactoScreen(navController) }
        composable("contacto_detalle") { ContactoDetalleScreen(navController) }
        composable("escanear_qr") { EscanearQrScreen(navController) }
        composable("galeria_qr") { GaleriaQrScreen(navController) }
        composable("escaneo_exitoso") { EscaneoExitosoScreen(navController) }
        composable("tarjetas_escaneadas") { TarjetasEscaneadasScreen(navController) }
        composable("detalle_tarjeta/{tarjetaId}") { backStackEntry ->
            val tarjetaId = backStackEntry.arguments?.getString("tarjetaId") ?: ""
            DetalleTarjetaScreen(navController, tarjetaId)
        }
        composable("crear_tarjeta") { CrearTarjetaScreen(navController) }
        composable("compartir_contacto") { CompartirContactoScreen(navController) }
        composable("info_usuario") { InfoUsuarioScreen(navController) }
        composable("descargar_formato") { DescargarFormatoScreen(navController) }
        composable("generar_qr/{tarjetaId}") { backStackEntry ->
            val tarjetaId = backStackEntry.arguments?.getString("tarjetaId") ?: ""
            GenerarQrScreen(navController, tarjetaId)
        }
        composable("enviar_correo/{correo}") { backStackEntry ->
            val correo = Uri.decode(backStackEntry.arguments?.getString("correo") ?: "")
            EnviarCorreoScreen(navController = navController, correo = correo)
        }
        composable("configuracion") { ConfiguracionScreen(navController) }
    }
}
