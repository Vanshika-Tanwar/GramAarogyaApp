package com.example.gramaarogya.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gramaarogya.R.drawable.prof

data class Consultation(val doctorName: String, val clinic: String, val date: String)

@Composable
@Preview
fun ProfileScreen() {
    val recentConsultations = listOf(
        Consultation("Dr.ABC", "Clinic 1", "12-09-2025"),
        Consultation("Dr.ABC", "Clinic 1", "11-09-2025"),
        Consultation("Dr.ABC", "Clinic 1", "10-09-2025"),
        Consultation("Dr.ABC", "Clinic 2", "08-09-2025")
    )

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(Color.White)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                modifier = Modifier.clickable { /* Handle back navigation */ }
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "Your Profile",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.Black
            )
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Spacer(modifier = Modifier.height(24.dp))
                Box(contentAlignment = Alignment.Center) {
                    Image(
                        painter = painterResource(prof),
                        contentDescription = "Profile Picture",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(150.dp)
                            .clip(CircleShape)
                            .border(2.dp, Color.Gray, CircleShape)
                    )
                }
                Spacer(modifier = Modifier.height(32.dp))
                // User Details Section
                ProfileDetailField(label = "Name", value = "Vanshika Tanwar")
                ProfileDetailField(label = "Phone Number", value = "+91 8700454942")
                ProfileDetailField(label = "Address", value = "XYZ")
                ProfileDetailField(label = "Date of Birth", value = "13-11-2004")
                ProfileDetailField(label = "Gender", value = "Female")
                Spacer(modifier = Modifier.height(20.dp))
                // Edit Profile Button
////                Button(
////                    onClick = { /* Handle Edit Profile click */ },
////                    modifier = Modifier
////                        .fillMaxWidth()
////                        .height(56.dp)
////                        .padding(horizontal = 16.dp),
////                    colors = ButtonDefaults.buttonColors(
////                        containerColor = Color.Black,
////                        contentColor = Color.White
////                    )
////                ) {
////                    Text(text = "Edit Profile", fontSize = 18.sp)
////                }
//                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    text = "Recent Consultations",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                )
            }
            items(recentConsultations) { consultation ->
                ConsultationCard(consultation = consultation)
                Spacer(modifier = Modifier.height(12.dp))
            }
            item {
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}


@Composable
fun ProfileDetailField(label: String, value: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = label,
            fontSize = 16.sp,
            color = Color.Black,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                .padding(16.dp)
        ) {
            Text(
                text = value,
                fontSize = 16.sp,
                color = Color.Black
            )
        }
    }
}

@Composable
fun ConsultationCard(consultation: Consultation) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "${consultation.doctorName}, ${consultation.clinic}",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = consultation.date,
                fontSize = 14.sp,
                color = Color.Gray
            )
        }
    }
}