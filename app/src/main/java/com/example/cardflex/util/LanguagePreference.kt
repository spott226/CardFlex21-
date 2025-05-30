package com.example.cardflex.util

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

// Delegado para crear DataStore
val Context.dataStore by preferencesDataStore(name = "settings")

object LanguagePreference {
    private val LANGUAGE_KEY = stringPreferencesKey("app_language")

    // Guardar idioma en DataStore
    suspend fun setLanguage(context: Context, language: String) {
        context.dataStore.edit { settings ->
            settings[LANGUAGE_KEY] = language
        }
    }

    // Obtener idioma guardado (o 'es' por defecto)
    suspend fun getLanguage(context: Context): String {
        return context.dataStore.data
            .map { preferences ->
                preferences[LANGUAGE_KEY] ?: "es"
            }
            .first()
    }

    // Función síncrona para obtener idioma
    fun getLanguageSync(context: Context): String {
        return kotlinx.coroutines.runBlocking {
            getLanguage(context)
        }
    }
}