package com.example.gramaarogya.Screens

import android.view.RoundedCorner
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gramaarogya.Models.DashboardFeatures
import com.example.gramaarogya.R
import com.example.gramaarogya.ui.theme.bgWhite
import com.example.gramaarogya.ui.theme.borderLightGrey
import com.example.gramaarogya.ui.theme.textLightGrey

@Composable
@Preview
fun Dashboard() {
    val featureList = listOf(
        DashboardFeatures(
            title = "Consult a Doctor",
            description = "desc",
            imgResID = R.drawable.logo
        ),
        DashboardFeatures(
            title = "Medicine Availability",
            description = "desc",
            imgResID = R.drawable.logo
        ),
        DashboardFeatures(
            title = "Health Records",
            description = "desc",
            imgResID = R.drawable.logo
        ),
        DashboardFeatures(
            title = "Symptom Checker",
            description = "desc",
            imgResID = R.drawable.logo
        ),
        DashboardFeatures(
            title = "Nearby Clinics",
            description = "desc",
            imgResID = R.drawable.logo
        )
    )
    val patientName = "default_name" // get name from DB

    Scaffold (
        containerColor = bgWhite,
        modifier = Modifier.fillMaxSize()
    ) {
        innerPadding ->
        Box (
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(color = bgWhite)
        ) {
            Column (
                modifier = Modifier
                    .background(bgWhite)
                    .fillMaxSize()
                    .padding(horizontal = 20.dp, vertical = 40.dp)
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(color = Color.Black)
                        .padding(horizontal = 15.dp, vertical = 8.dp)
                ) {
                    Text(text = "Welcome $patientName", fontSize = 20.sp, style = TextStyle(color = bgWhite), fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.height(70.dp))
                Text(text = "How can we help you", style = TextStyle(color = Color.Black), fontSize = 28.sp, fontWeight = FontWeight.SemiBold)
                Box (
                    modifier = Modifier
                        .fillMaxHeight(0.6f)
                        .shadow(
                            elevation = 8.dp,
                            spotColor = Color.Black.copy(alpha = 0.3f),
                            ambientColor = Color.Black.copy(alpha = 0.1f),
                            shape = RoundedCornerShape(12.dp)
                        )
                        .background(Color.White, RoundedCornerShape(12.dp))
                        .border(
                            border = BorderStroke(width = 1.dp, color = borderLightGrey),
                            shape = RoundedCornerShape(12.dp)
                        )
                ) {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                            // .clip(RoundedCornerShape(16.dp))
                            // .fillMaxHeight(0.68f)
                            // .background(Color.White)
                    ) {
                        items(featureList) { features ->
                            displayFeatures(
                                dashboardFeatures = features,
                                onClick = { }
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(25.dp))
                Image(
                    painterResource(R.drawable.logo),
                    contentDescription = "logo",
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}

@Composable
fun displayFeatures(dashboardFeatures: DashboardFeatures, onClick: () -> Unit) {
    Row (
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp, horizontal = 16.dp)
            .clickable { onClick() }
    ) {
        Image(
            painterResource(dashboardFeatures.imgResID),
            contentDescription = "feature logo",
            modifier = Modifier.size(60.dp)
        )
        Spacer(modifier = Modifier.height(15.dp))
        Column (
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = dashboardFeatures.title, style = TextStyle(color = Color.Black), fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
            Text(text = dashboardFeatures.description, style = TextStyle(color = textLightGrey), fontSize = 14.sp)
        }
    }
}