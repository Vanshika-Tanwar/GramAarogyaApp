package com.example.gramaarogya.Navigation

sealed class GramAarogyaAppNavItem(val route: String) {
    object splashScreen : GramAarogyaAppNavItem("splash")
    object langScreen : GramAarogyaAppNavItem("lang")
    object phnScreen : GramAarogyaAppNavItem("phn")
    object otpScreen : GramAarogyaAppNavItem("otp")
    object successScreen : GramAarogyaAppNavItem("success")
    object dashboardScreen : GramAarogyaAppNavItem("dashboard")
    object vidScreen : GramAarogyaAppNavItem("videocall")
    object clinicScreen : GramAarogyaAppNavItem("nearbyclinic")
    object profScreen : GramAarogyaAppNavItem("profile")
    object botScreen : GramAarogyaAppNavItem("chatbot")

}