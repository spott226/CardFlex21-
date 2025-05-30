package com.example.cardflex

import com.google.firebase.auth.FirebaseAuth

object FirebaseAuthHelper {
    private val auth = FirebaseAuth.getInstance()

    /**
     * Registra un nuevo usuario con correo y contraseña.
     */
    fun registrarUsuario(email: String, password: String, onResult: (Boolean, String?) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onResult(true, null)
                } else {
                    onResult(false, task.exception?.message)
                }
            }
    }

    /**
     * Inicia sesión con correo y contraseña.
     */
    fun iniciarSesion(email: String, password: String, onResult: (Boolean, String?) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onResult(true, null)
                } else {
                    onResult(false, task.exception?.message)
                }
            }
    }

    /**
     * Cierra sesión del usuario actual.
     */
    fun cerrarSesion() {
        auth.signOut()
    }

    /**
     * Retorna el usuario actualmente autenticado (o null si no hay).
     */
    fun obtenerUsuarioActual() = auth.currentUser

    /**
     * Envía un correo de recuperación de contraseña al correo del usuario actual.
     */
    fun enviarCorreoRecuperacion(onResult: (Boolean, String?) -> Unit) {
        val email = auth.currentUser?.email
        if (email != null) {
            auth.sendPasswordResetEmail(email)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        onResult(true, null)
                    } else {
                        onResult(false, task.exception?.message)
                    }
                }
        } else {
            onResult(false, "No se pudo obtener el correo del usuario actual.")
        }
    }

    /**
     * Cambia el correo electrónico del usuario actual.
     * ⚠️ Puede requerir reautenticación si la sesión es antigua.
     */
    fun actualizarCorreo(nuevoCorreo: String, onResult: (Boolean, String?) -> Unit) {
        val user = auth.currentUser
        if (user != null) {
            user.updateEmail(nuevoCorreo)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        onResult(true, null)
                    } else {
                        onResult(false, task.exception?.message)
                    }
                }
        } else {
            onResult(false, "No hay usuario autenticado.")
        }
    }
}