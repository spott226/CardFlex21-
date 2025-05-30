package com.example.cardflex

import android.app.Activity
import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

object GoogleAuthHelper {
    lateinit var googleSignInClient: GoogleSignInClient

    // 🔧 Inicializa el cliente de Google con el Client ID Web
    fun initGoogleClient(activity: Activity) {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("224126911992-rd9dkqbc4m0vsea3io4eh09454mdbs6s.apps.googleusercontent.com") // Tu Client ID Web
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(activity, gso)
    }

    // 🚀 Lanza el intent de inicio de sesión con Google
    fun iniciarGoogleSignIn(activity: Activity, onComplete: (Boolean, String?) -> Unit) {
        initGoogleClient(activity)
        val signInIntent = googleSignInClient.signInIntent
        activity.startActivityForResult(signInIntent, 1001)
        // El resultado se maneja en MainActivity.onActivityResult
    }

    // 🔐 Inicia sesión con Firebase usando la cuenta de Google
    fun firebaseAuthWithGoogle(account: GoogleSignInAccount, onComplete: (Boolean, String?) -> Unit) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        FirebaseAuth.getInstance().signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onComplete(true, null)
                } else {
                    onComplete(false, task.exception?.message)
                }
            }
    }

    // 📥 Maneja el resultado del intent y autentica con Firebase
    fun manejarResultado(requestCode: Int, data: Intent?, onComplete: (Boolean, String?) -> Unit) {
        if (requestCode == 1001) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.result
                if (account != null) {
                    firebaseAuthWithGoogle(account, onComplete)
                } else {
                    onComplete(false, "Cuenta de Google no encontrada")
                }
            } catch (e: Exception) {
                onComplete(false, e.message)
            }
        }
    }
}
