package com.example.cardflex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.cardflex.navigation.AppNavigation
import androidx.compose.material3.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                val navController = rememberNavController()
                AppNavigation(navController)
            }
        }
    }
}
