package com.example.cardflex

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.cardflex.navigation.AppNavigation
import androidx.compose.material3.MaterialTheme
import com.example.cardflex.GoogleAuthHelper
import com.example.cardflex.util.LanguagePreference
import com.example.cardflex.util.LocaleUtils

class MainActivity : FragmentActivity() {

    override fun attachBaseContext(newBase: Context) {
        val lang = LanguagePreference.getLanguageSync(newBase)
        val context = LocaleUtils.setLocale(newBase, lang)
        super.attachBaseContext(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                val navController = rememberNavController()
                AppNavigation(navController)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        GoogleAuthHelper.manejarResultado(requestCode, data) { success, error ->
            if (success) {
                println("✅ Inicio de sesión con Google exitoso")
            } else {
                println("❌ Falló el login con Google: $error")
            }
        }
    }
}