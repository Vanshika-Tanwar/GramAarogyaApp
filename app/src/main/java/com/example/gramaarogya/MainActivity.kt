package com.example.gramaarogya

import android.os.Bundle
import android.window.SplashScreen
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.gramaarogya.Navigation.GramAarogyaAppNav
import com.example.gramaarogya.Navigation.GramAarogyaAppNavItem
import com.example.gramaarogya.Screens.Dashboard
import com.example.gramaarogya.Screens.MapScreen
import com.example.gramaarogya.Screens.MapsViewModel
import com.example.gramaarogya.Screens.NearbyClinics
import com.example.gramaarogya.Screens.SelectLanguage
import com.example.gramaarogya.Screens.SplashScreen
import com.example.gramaarogya.ui.theme.GramAarogyaTheme
import com.google.firebase.appcheck.FirebaseAppCheck
import com.google.firebase.appcheck.playintegrity.PlayIntegrityAppCheckProviderFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val firebaseAppCheck = FirebaseAppCheck.getInstance()
        firebaseAppCheck.installAppCheckProviderFactory (
            PlayIntegrityAppCheckProviderFactory.getInstance()
        )
        setContent {
            val navHostController = rememberNavController()
            GramAarogyaAppNav(navHostController = navHostController)
        }
    }
}
