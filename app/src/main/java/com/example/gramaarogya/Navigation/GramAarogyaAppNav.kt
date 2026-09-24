package com.example.gramaarogya.Navigation

import OTPScreen
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.gramaarogya.Screens.ChatBotScreen
import com.example.gramaarogya.Screens.ChatViewModel
import com.example.gramaarogya.Screens.Dashboard
import com.example.gramaarogya.Screens.NearbyClinics
import com.example.gramaarogya.Screens.PhnScreen
import com.example.gramaarogya.Screens.ProfileScreen
import com.example.gramaarogya.Screens.SelectLanguage
import com.example.gramaarogya.Screens.SignIn.SignInScreen
import com.example.gramaarogya.Screens.SignUp.RegisterScreen
import com.example.gramaarogya.Screens.SplashScreen
import com.example.gramaarogya.Screens.SuccessScreen
import com.example.gramaarogya.Screens.VideoCallScreen

@Composable
fun GramAarogyaAppNav(navHostController : NavHostController){
    NavHost(navController = navHostController, startDestination = GramAarogyaAppNavItem.splashScreen.route) {

        composable(GramAarogyaAppNavItem.splashScreen.route){
            SplashScreen(navHostController)
        }
        composable(GramAarogyaAppNavItem.langScreen.route){
            SelectLanguage(navHostController)
        }
        composable(GramAarogyaAppNavItem.phnScreen.route){
            PhnScreen(navHostController)
        }
        composable(GramAarogyaAppNavItem.otpScreen.route){
            OTPScreen(navHostController)
        }
        composable(GramAarogyaAppNavItem.successScreen.route){
            SuccessScreen(navHostController)
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
        composable(GramAarogyaAppNavItem.SignUpScreen.route){
            RegisterScreen(navHostController)
        }
        composable(GramAarogyaAppNavItem.SignInScreen.route){
            SignInScreen(navHostController)
        }
    }


}