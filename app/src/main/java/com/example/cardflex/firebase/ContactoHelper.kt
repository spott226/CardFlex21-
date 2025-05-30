package com.example.cardflex.firebase

import com.example.cardflex.model.Contacto
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

// Guarda un contacto en Firebase bajo el UID del usuario autenticado
fun guardarContacto(
    contacto: Contacto,
    onSuccess: () -> Unit,
    onFailure: (Exception) -> Unit
) {
    val userId = FirebaseAuth.getInstance().currentUser?.uid
    if (userId == null) {
        onFailure(Exception("Usuario no autenticado"))
        return
    }

    val ref = FirebaseDatabase.getInstance()
        .getReference("contacts")
        .child(userId)
        .push()

    ref.setValue(contacto)
        .addOnSuccessListener {
            println("Contacto guardado: $contacto")
            onSuccess()
        }
        .addOnFailureListener {
            println("Error al guardar contacto: ${it.message}")
            onFailure(it)
        }
}

// Elimina un contacto por coincidencia exacta de sus campos
fun eliminarContactoPorDatos(
    contacto: Contacto,
    onSuccess: () -> Unit,
    onFailure: (Exception) -> Unit
) {
    val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
    val ref = FirebaseDatabase.getInstance().getReference("contacts").child(userId)

    ref.get().addOnSuccessListener { snapshot ->
        for (child in snapshot.children) {
            val c = child.getValue(Contacto::class.java)
            if (c?.nombre == contacto.nombre &&
                c.apellido == contacto.apellido &&
                c.telefono == contacto.telefono &&
                c.correo == contacto.correo
            ) {
                child.ref.removeValue()
                    .addOnSuccessListener { onSuccess() }
                    .addOnFailureListener { onFailure(it) }
                return@addOnSuccessListener
            }
        }
        onFailure(Exception("Contacto no encontrado"))
    }.addOnFailureListener {
        onFailure(it)
    }
}