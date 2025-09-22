package com.example.gramaarogya.Navigation

import OTPScreen
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.gramaarogya.Screens.Dashboard
import com.example.gramaarogya.Screens.PhnScreen
import com.example.gramaarogya.Screens.SelectLanguage
import com.example.gramaarogya.Screens.SplashScreen
import com.example.gramaarogya.Screens.SuccessScreen

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
            (navHostController)
        }
    }


}