package com.example.gramaarogya.Screens.chatbot

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.gramaarogya.Models.Messages
import com.example.gramaarogya.ui.theme.bgWhite
import com.example.gramaarogya.ui.theme.borderLightGrey
import com.example.gramaarogya.ui.theme.chatModelResponseGrey
import com.example.gramaarogya.ui.theme.textLightGrey

// gemini api key AIzaSyCE9AY0_gIvCdSc9yldTD1dy2xy8CL5nCg
@Composable
fun ChatBotScreen(chatViewModel: ChatViewModel, navHostController: NavHostController) {
    Scaffold (
        containerColor = bgWhite,
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column (
            modifier = Modifier.padding(innerPadding)
        ) {
            //Text(text = "chatbot")
            ChatBotHeader()
            // BOX -> LAZY COLUMN -> { message mutable list }
            MessageList(modifier = Modifier.weight(1f),chatViewModel.messageList)
            MessageInput(onMessageSend = {
                chatViewModel.sendMessage(it)
            })
        }
    }
}

@Composable
fun MessageList(modifier: Modifier = Modifier, messageList: List<Messages>) {
    if(messageList.isEmpty()) {
        Column (
            modifier = modifier.fillMaxSize().padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "An apple a day keeps the doctor away!",
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                color = Color.Black,
                textAlign = TextAlign.Center
            )
        }
    } else {
        LazyColumn (
            modifier = modifier,
            reverseLayout = true
        ) {
            items(messageList.reversed()) {
                MessageRow(messageModel = it)
            }
        }
    }

}

@Composable
fun MessageRow(messageModel : Messages) {
    val isModel = messageModel.role == "model"
    Row (
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box (
            modifier = Modifier.fillMaxWidth()
        ) {
            Box (
                modifier = Modifier
                    .align(if(isModel) Alignment.BottomStart else Alignment.BottomEnd)
                    .padding(
                        start = if(isModel) 8.dp else 70.dp,
                        end = if(isModel) 70.dp else 8.dp,
                        top = 8.dp,
                        bottom = 8.dp
                    )
                    .clip(RoundedCornerShape(16.dp))
                    .background(if(isModel) chatModelResponseGrey else Color.Black)
                    .padding(14.dp)
            ) {
                Text(
                    text = messageModel.message,
                    fontWeight = FontWeight.W500,
                    style = TextStyle(color = if(isModel) Color.Black else Color.White)
                )
            }
        }
    }
}

@Composable
fun MessageInput(onMessageSend: (String)->Unit) {
    var message by remember { mutableStateOf("") }
    Row (
        modifier = Modifier
            .padding(all = 8.dp)
            .fillMaxWidth()
            .background(color = bgWhite)
    ){
        OutlinedTextField(
            shape = RoundedCornerShape(30.dp),
            modifier = Modifier
                .weight(1f)
                .padding(4.dp),
            value = message,
            onValueChange = {
                message = it
            },
            textStyle = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.SemiBold),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black
            ),
            placeholder = { Text(text = "Ask Doctor Sahab...") }
        )
        IconButton(
            modifier = Modifier.align(Alignment.CenterVertically),
            onClick = {
                if(message.isNotEmpty()) {
                    onMessageSend(message)
                    message = ""
                }
            }
        ) {
            Icon(
                imageVector = Icons.Default.Send,
                contentDescription = "Send Button"
            )
        }
    }
}

@Composable
fun ChatBotHeader() {
    Box (
        modifier = Modifier
            .shadow(
                elevation = 8.dp,
                spotColor = Color.Black.copy(alpha = 0.3f),
                ambientColor = Color.Black.copy(alpha = 0.1f)
            )
            .border(
                border = BorderStroke(width = 1.dp, color = borderLightGrey)
            )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.White)
                .padding(top = 10.dp, bottom = 10.dp)

        ) {
            Text(
                text = "Doctor Sahab",
                style = TextStyle(
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp
                )
            )
            Text(text = "your health assistant", style = TextStyle(color = textLightGrey))
        }
    }
}