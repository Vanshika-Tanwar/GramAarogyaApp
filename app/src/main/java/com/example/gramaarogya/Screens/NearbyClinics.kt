package com.example.gramaarogya.Screens
// maps api key : AIzaSyB6IxaqsuefQ9RqVSI0QI4hehMfKhLFq0U
import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialogDefaults.containerColor
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarColors
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gramaarogya.Models.Clinics
import com.example.gramaarogya.ui.theme.bgWhite
import com.example.gramaarogya.ui.theme.borderLightGrey
import com.example.gramaarogya.ui.theme.searchBarGrey
import com.example.gramaarogya.ui.theme.textLightGrey
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.input.ImeAction
@SuppressLint("ViewModelConstructorInComposable")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun NearbyClinics() {
    val clinicList = listOf(
        Clinics(
            id = 1,
            title = "Harmony Health Clinic",
            address = "SCO 12, Phase 3B2, Mohali, Punjab",
            phoneNo = 9876543210,
            lat = 30.7046,
            lng = 76.7179
        ),
        Clinics(
            id = 2,
            title = "Sunrise Medical Center",
            address = "Model Town, Ludhiana, Punjab",
            phoneNo = 9123456780,
            lat = 30.9000,
            lng = 75.8573
        ),
        Clinics(
            id = 3,
            title = "Green Cross Clinic",
            address = "Ranjit Avenue, Amritsar, Punjab",
            phoneNo = 9988776655,
            lat = 31.6340,
            lng = 74.8723
        ),
        Clinics(
            id = 4,
            title = "Lifeline Health Point",
            address = "Urban Estate, Patiala, Punjab",
            phoneNo = 9001122334,
            lat = 30.3398,
            lng = 76.3869
        ),
        Clinics(
            id = 5,
            title = "CityCare Clinic",
            address = "Ferozepur Road, Bathinda, Punjab",
            phoneNo = 9445566778,
            lat = 30.2110,
            lng = 74.9455
        )
    )

    var text by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }

    Scaffold (
        containerColor = bgWhite,
        modifier = Modifier.fillMaxSize()
    ) {
        innerPadding ->
        Box (
            modifier = Modifier
                .padding(innerPadding)
                .background(bgWhite)
                .fillMaxSize()
                //.padding(10.dp)
        ) {
            Column(
                modifier = Modifier
                    //.padding(horizontal = 5.dp)
                    .background(bgWhite)
                    .fillMaxSize()
                    .align(Alignment.Center)
            ) {
                CustomSearchBar(
                    text = text,
                    onTextChange = { text = it },
                    onSearch = {
                        // Perform your search query here
                        active = false
                        // Log the search query for debugging
                        println("Searching for: $text")
                    },
                    onCloseClick = {
                        text = ""
                        active = false
                    },
                    active = active,
                    modifier = Modifier.padding(top = 16.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                Box (
                    modifier = Modifier.padding(horizontal = 10.dp)
                ) {
                    if (text.isEmpty() && !active) {
                        Text(text = "Start typing to search for clinics...")
                    } else {
                        Text(text = "Displaying search results for '$text'")
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Box(
                    modifier = Modifier
                        .background(Color.White)
                        .fillMaxWidth()
                        .fillMaxHeight(0.45f)
//                        .shadow(
//                            elevation = 16.dp,
//                            spotColor = Color.Black.copy(alpha = 0.6f),
//                            ambientColor = Color.Black.copy(alpha = 0.5f)
//                        )
                ) {
                    MapScreen(MapsViewModel())
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomCenter) // Align to the bottom of the map container
                            .fillMaxWidth()
                            .height(25.dp)
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(Color.Transparent, bgWhite),
                                    startY = 0f,
                                    endY = Float.POSITIVE_INFINITY
                                )
                            )
                    )
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopCenter) // Align to the bottom of the map container
                            .fillMaxWidth()
                            .height(25.dp)
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(bgWhite, Color.Transparent),
                                    startY = 0f,
                                    endY = Float.POSITIVE_INFINITY
                                )
                            )
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Box (
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp)
                        .shadow(
                            elevation = 8.dp,
                            spotColor = Color.Black.copy(alpha = 0.3f),
                            ambientColor = Color.Black.copy(alpha = 0.1f),
                            shape = RoundedCornerShape(10.dp)
                        )
                        .background(Color.White, RoundedCornerShape(10.dp))
                        .border(
                            border = BorderStroke(width = 1.dp, color = borderLightGrey),
                            shape = RoundedCornerShape(10.dp)
                        )
                ) {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize().padding(vertical = 6.dp)
                    ) {
                        items(clinicList) { clinics ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp, vertical = 6.dp),
                                shape = RoundedCornerShape(12.dp),
                                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = searchBarGrey
                                )
                            ) {
                                // Your existing displayClinics composable goes here
                                displayClinics(
                                    clinics = clinics
                                )
                            }
//                            displayClinics(
//                                clinics = clinics
//                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun displayClinics(clinics: Clinics) {
    Row (
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 5.dp)
    ) {
//        Icon(
//            imageVector = Icons.Default.AddCircle,
//            contentDescription = "hospital plus sign"
//        )
//        Spacer(modifier = Modifier.width(10.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
        ) {
            Text(text = clinics.title, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Row (
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = "location symbol"
                )
                Spacer(modifier = Modifier.width(2.dp))
                Text(
                    text = clinics.address,
                    style = TextStyle(color = textLightGrey)
                )
            }
            Row (
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Call,
                    contentDescription = "phone symbol"
                )
                Spacer(modifier = Modifier.width(2.dp))
                Icon(
                    imageVector = Icons.Default.Videocam,
                    contentDescription = "phone symbol"
                )
                Spacer(modifier = Modifier.width(2.dp))
                Text(
                    text = clinics.phoneNo.toString(),
                    style = TextStyle(color = textLightGrey),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Composable
fun CustomSearchBar(
    text: String,
    onTextChange: (String) -> Unit,
    onSearch: () -> Unit,
    onCloseClick: () -> Unit,
    modifier: Modifier = Modifier,
    active: Boolean
) {
    Column (
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = text,
            onValueChange = onTextChange,
            modifier = modifier
                .fillMaxWidth(0.9f)
                .shadow(
                    elevation = 8.dp,
                    shape = RoundedCornerShape(10.dp),
                    spotColor = Color.Black.copy(alpha = 0.6f),
                    ambientColor = Color.Black.copy(alpha = 0.5f)
                )
                .border(
                    border = BorderStroke(width = 0.5.dp, color = borderLightGrey),
                    shape = RoundedCornerShape(10.dp)
                ),
            placeholder = {
                Text(text = "Search clinics", color = Color.Gray)
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search Icon"
                )
            },
            trailingIcon = {
                if (active) {
                    Icon(
                        modifier = Modifier.clickable { onCloseClick() },
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close Icon"
                    )
                }
            },
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                disabledContainerColor = Color.White,
                cursorColor = Color.Black,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(10.dp),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = { onSearch() }
            )
        )
    }
}
