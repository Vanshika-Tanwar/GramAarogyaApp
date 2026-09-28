package com.example.gramaarogya

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.gramaarogya.Database.AppDatabase
import com.example.gramaarogya.Navigation.GramAarogyaAppNav
import com.example.gramaarogya.Repository.Auth.AuthRepository
import com.example.gramaarogya.Screens.SplashScreen


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val database = AppDatabase.getDatabase(applicationContext)

            val authRepository = AuthRepository(
                database.userDao()
            )
            val navHostController = rememberNavController()
            GramAarogyaAppNav(navHostController = navHostController)
        }
    }
}
