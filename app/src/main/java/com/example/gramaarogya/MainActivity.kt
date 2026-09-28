package com.example.gramaarogya

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.gramaarogya.Database.AppDatabase
import com.example.gramaarogya.Navigation.GramAarogyaAppNav
import com.example.gramaarogya.Repository.Auth.AuthRepository
import com.google.firebase.appcheck.FirebaseAppCheck
import com.google.firebase.appcheck.playintegrity.PlayIntegrityAppCheckProviderFactory


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val firebaseAppCheck = FirebaseAppCheck.getInstance()
       firebaseAppCheck.installAppCheckProviderFactory(
           PlayIntegrityAppCheckProviderFactory.getInstance()
        )
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
