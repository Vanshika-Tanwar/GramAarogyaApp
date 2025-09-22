package com.example.gramaarogya

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModelProvider
import com.example.gramaarogya.Screens.ChatBotScreen
import com.example.gramaarogya.Screens.ChatViewModel
import com.example.gramaarogya.Screens.Dashboard
import com.example.gramaarogya.Screens.NearbyClinics
import com.example.gramaarogya.Screens.SelectLanguage
import com.example.gramaarogya.Screens.SplashScreen
import com.example.gramaarogya.Screens.VideoCallScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val chatViewModel = ViewModelProvider(this)[ChatViewModel :: class.java]
        setContent {
            //SplashScreen()
            //SelectLanguage()
            //Dashboard()
            NearbyClinics()
            //ChatBotScreen(chatViewModel)
            //VideoCallScreen()
        }
    }
}
