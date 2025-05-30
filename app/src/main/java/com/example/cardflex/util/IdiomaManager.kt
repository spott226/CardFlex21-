package com.example.cardflex.util

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

object IdiomaManager {
    private val _idioma = MutableStateFlow("es") // Valor inicial Español
    val idioma: StateFlow<String> = _idioma.asStateFlow()

    // Carga el idioma guardado de forma suspendida
    fun cargarIdioma(context: Context) {
        CoroutineScope(Dispatchers.IO).launch {
            val savedLang = LanguagePreference.getLanguage(context)
            _idioma.value = savedLang
        }
    }

    // Cambia el idioma y actualiza estado
    fun cambiarIdioma(context: Context, nuevo: String) {
        CoroutineScope(Dispatchers.IO).launch {
            LanguagePreference.setLanguage(context, nuevo)
            _idioma.value = nuevo
        }
    }
}
