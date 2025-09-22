package com.example.gramaarogya.Screens
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CallEnd
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MicOff
import androidx.compose.material.icons.filled.SpeakerPhone
import androidx.compose.material.icons.filled.SwitchCamera
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.gramaarogya.R

@Composable
fun VideoCallScreen(navHostController: NavHostController) {
    var isMicMuted by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        // Main video feed (e.g., the doctor)
        Image(
            painter = painterResource(id = R.drawable.doctor_stock_image),
            contentDescription = "Doctor's video feed",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Column (modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 30.dp, start = 16.dp, end = 16.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.Gray.copy(alpha = 0.5f))
                    .padding(4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.padding(8.dp),
                    text = "05:23",
                    color = Color.Black,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                IconButton(
                    onClick = { /* Handle camera switch */ },
                    modifier = Modifier
                        .size(52.dp)
                        .clip(CircleShape)
                        //.background(Color.Gray.copy(alpha = 0.5f))
                ) {
                    Icon(
                        imageVector = Icons.Default.SwitchCamera,
                        contentDescription = "Switch Camera",
                        tint = Color.Black
                    )
                }
            }

            // Patient's self-view in a floating window
            Image(
                painter = painterResource(id = R.drawable.patient_stock_image),
                contentDescription = "Patient's video feed",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(120.dp, 160.dp)
                    .align(Alignment.End)
                    .padding(16.dp)
                    .clip(RoundedCornerShape(12.dp))
            )
        }

        // Bottom control bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Mute button
            IconButton(
                onClick = { isMicMuted = !isMicMuted },
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(Color.Gray.copy(alpha = 0.5f))
            ) {
                Icon(
                    imageVector = if (isMicMuted) Icons.Default.MicOff else Icons.Default.Mic,
                    contentDescription = "Mute Mic",
                    tint = Color.White
                )
            }

            // End call button
            Button(
                onClick = { /* Handle end call */ },
                modifier = Modifier.size(64.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                shape = CircleShape
            ) {
                Icon(
                    imageVector = Icons.Default.CallEnd,
                    contentDescription = "End Call",
                    tint = Color.White
                )
            }

            // Speakerphone button
            IconButton(
                onClick = { /* Handle speakerphone toggle */ },
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(Color.Gray.copy(alpha = 0.5f))
            ) {
                Icon(
                    imageVector = Icons.Default.SpeakerPhone,
                    contentDescription = "Speakerphone",
                    tint = Color.White
                )
            }
        }
    }
}

