package com.example.gramaarogya.Navigation

sealed class GramAarogyaAppNavItem(val route: String) {
    object splashScreen : GramAarogyaAppNavItem("splash")
    object langScreen : GramAarogyaAppNavItem("lang")
    object dashboardScreen : GramAarogyaAppNavItem("dashboard")
    object vidScreen : GramAarogyaAppNavItem("videocall")
    object clinicScreen : GramAarogyaAppNavItem("nearbyclinic")
    object profScreen : GramAarogyaAppNavItem("profile")
    object botScreen : GramAarogyaAppNavItem("chatbot")

}