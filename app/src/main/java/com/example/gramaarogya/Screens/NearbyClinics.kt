package com.example.gramaarogya.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialogDefaults.containerColor
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarColors
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gramaarogya.ui.theme.bgWhite
import com.example.gramaarogya.ui.theme.searchBarGrey

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun NearbyClinics() {
    var text by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }

    Scaffold (
        containerColor = bgWhite,
        modifier = Modifier.fillMaxSize()
    ) {
        innerPadding ->
        Column (
            modifier = Modifier
                .padding(innerPadding)
                .background(bgWhite)
                .fillMaxSize()
        ) {
            SearchBar(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .align(Alignment.CenterHorizontally),
                query = text,
                onQueryChange = { text = it },
                onSearch = { active = false },
                active = active,
                onActiveChange = { active = it },
                placeholder = { Text(text = "Search clinics") },
                leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = "search icon") },
                trailingIcon = {
                    if(active) {
                        Icon(
                            modifier = Modifier.clickable {
                                if (text.isNotEmpty()) { text = "" }
                                else { active = false }
                            },
                            imageVector = Icons.Default.Close,
                            contentDescription = "close icon"
                        )
                    }
                }
            ) {
                 // IMPLEMENT SEARCH CLINICS QUERIES
            }

        }
    }
}