package com.example.gramaarogya.Navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.gramaarogya.Screens.chatbot.ChatBotScreen
import com.example.gramaarogya.Screens.chatbot.ChatViewModel
import com.example.gramaarogya.Screens.dashboard.Dashboard
import com.example.gramaarogya.Screens.nearby.NearbyClinics
import com.example.gramaarogya.Screens.profile.ProfileScreen
import com.example.gramaarogya.Screens.lang.SelectLanguage
import com.example.gramaarogya.Screens.splash.SplashScreen
import com.example.gramaarogya.Screens.videocall.VideoCallScreen

@Composable
fun GramAarogyaAppNav(navHostController : NavHostController){
    NavHost(navController = navHostController, startDestination = GramAarogyaAppNavItem.splashScreen.route) {

        composable(GramAarogyaAppNavItem.splashScreen.route){
            SplashScreen(navHostController)
        }
        composable(GramAarogyaAppNavItem.langScreen.route){
            SelectLanguage(navHostController)
        }
        composable(GramAarogyaAppNavItem.dashboardScreen.route){
            Dashboard(navHostController)
        }
        composable(GramAarogyaAppNavItem.vidScreen.route){
            VideoCallScreen(navHostController)
        }
        composable(GramAarogyaAppNavItem.clinicScreen.route){
            NearbyClinics(navHostController)
        }
        composable(GramAarogyaAppNavItem.profScreen.route){
            ProfileScreen(navHostController)
        }
        composable(GramAarogyaAppNavItem.botScreen.route) {
            // Get the ViewModel instance using viewModel()
            val chatViewModel: ChatViewModel = viewModel()
            ChatBotScreen(chatViewModel = chatViewModel, navHostController = navHostController)
        }


    }


}