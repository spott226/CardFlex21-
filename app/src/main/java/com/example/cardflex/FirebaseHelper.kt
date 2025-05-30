package com.example.cardflex

import com.example.cardflex.model.Usuario
import com.example.cardflex.model.Tarjeta
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

object FirebaseHelper {

    // 🔵 Referencia a la tabla "usuarios"
    private val dbRef: DatabaseReference = FirebaseDatabase.getInstance().getReference("usuarios")

    // 🟢 Referencia a la tabla "tarjetas"
    private val tarjetasRef: DatabaseReference = FirebaseDatabase.getInstance().getReference("tarjetas")

    // -------------------------
    // FUNCIONES PARA USUARIOS
    // -------------------------

    fun insertarUsuario(usuario: Usuario) {
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return
        dbRef.child(uid).setValue(usuario)
    }

    fun obtenerUsuario(uid: String, listener: ValueEventListener) {
        dbRef.child(uid).addListenerForSingleValueEvent(listener)
    }

    fun actualizarUsuario(uid: String, usuario: Usuario) {
        dbRef.child(uid).setValue(usuario)
    }

    fun eliminarUsuario(uid: String) {
        dbRef.child(uid).removeValue()
    }

    // -------------------------
    // FUNCIONES PARA TARJETAS
    // -------------------------

    fun guardarTarjeta(tarjeta: Tarjeta, onSuccess: () -> Unit, onError: (String) -> Unit) {
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        if (uid == null) {
            onError("Usuario no autenticado")
            return
        }

        val nuevaRef = tarjetasRef.child(uid).push()
        nuevaRef.setValue(tarjeta)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { error -> onError(error.message ?: "Error desconocido") }
    }

    fun obtenerTarjetas(uid: String, listener: ValueEventListener) {
        tarjetasRef.child(uid).addListenerForSingleValueEvent(listener)
    }

    fun eliminarTarjeta(uid: String, tarjetaId: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        tarjetasRef.child(uid).child(tarjetaId).removeValue()
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { error -> onError(error.message ?: "Error al eliminar") }
    }
}