package com.example.gramaarogya.Screens.lang

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.gramaarogya.Navigation.GramAarogyaAppNavItem
import com.example.gramaarogya.R
import com.example.gramaarogya.ui.theme.bgWhite
import com.example.gramaarogya.ui.theme.optionColor
import com.example.gramaarogya.ui.theme.selectionColor
import com.example.gramaarogya.ui.theme.textGrey

@Composable
fun SelectLanguage(navHostController: NavHostController) {

    var selected_language by remember { mutableStateOf<String?>(null) }

    Scaffold (
        containerColor = bgWhite,
        modifier = Modifier.fillMaxSize()
    ) {
        innerPadding ->
        Box (
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Column (
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxSize()
            ) {
                Image(
                    painterResource(id = R.drawable.logo),
                    contentDescription = "logo",
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .size(300.dp)
                )
                Spacer(modifier = Modifier.height(20.dp))
                Column (
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text(text = "Choose your language", fontSize = 24.sp, style = TextStyle(color = textGrey), fontWeight = FontWeight.Bold, modifier = Modifier.align(Alignment.CenterHorizontally))
                    Text(text = "(भाषा चुनें)", fontSize = 24.sp, style = TextStyle(color = textGrey), fontWeight = FontWeight.Bold, modifier = Modifier.align(Alignment.CenterHorizontally))
                    // 1. angreji beat te
                    langaugeOption(
                        "English",
                        "en",
                        isSelected = (selected_language == "en"),
                        onSelect = { selected_language = "en" }
                    )
                    Spacer(modifier = Modifier.height(7.dp))
                    // 2. matrbhasha hindi
                    langaugeOption(
                        "Hindi",
                        "hi",
                        isSelected = (selected_language == "hi"),
                        onSelect = { selected_language = "hi" }
                    )
                    Spacer(modifier = Modifier.height(7.dp))
                    // 3. straight outta moosa pind
                    langaugeOption(
                        "Punjabi",
                        "pu",
                        isSelected = (selected_language == "pu"),
                        onSelect = { selected_language = "pu" }
                    )
                    Spacer(modifier = Modifier.height(7.dp))
                    Button(
                        // selected lang ko ek data class mai store krwa denge / pass on to next screen
                        onClick = {navHostController.navigate(GramAarogyaAppNavItem.dashboardScreen.route) },
                        enabled = (selected_language != null),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Black,
                            contentColor = Color.Black,
                            disabledContentColor = Color.Black,
                            disabledContainerColor = Color.Black
                        ),
                        modifier = Modifier
                            //.clip(RoundedCornerShape(16.dp))
                            .fillMaxWidth(0.6f)
                            .align(Alignment.CenterHorizontally)
                    ) {
                        Text(text = "Submit", style = TextStyle(color = bgWhite), fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
fun langaugeOption (
    lang: String,
    lang_code: String,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .fillMaxWidth(0.85f)
            .background(color = if(isSelected) selectionColor else optionColor)
            .clickable { onSelect() }
            .padding(8.dp)
    ) {
        Text(text = lang, modifier = Modifier.align(Alignment.Center))
    }
}