package com.example.gramaarogya.Screens.splash

import android.os.Handler
import android.os.Looper
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.gramaarogya.Navigation.GramAarogyaAppNavItem
import com.example.gramaarogya.R
import com.example.gramaarogya.ui.theme.bgWhite
import com.example.gramaarogya.ui.theme.selectionColor

@Composable
fun SplashScreen(navHostController: NavHostController?=null) {
    Scaffold(
        containerColor = bgWhite,
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(bgWhite)
        ) {
            Image(
                painterResource(id = R.drawable.logo),
                contentDescription = "logo",
                modifier = Modifier
                    .size(300.dp)
                    .align(Alignment.Center)
            )
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(40.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(35.dp),
                strokeWidth = 5.dp,
                color = selectionColor
            )
        }
        Handler(Looper.getMainLooper()).postDelayed(
            Runnable { navHostController?.navigate(GramAarogyaAppNavItem.langScreen.route) },
            2500
        )
    }
}