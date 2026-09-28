package com.example.gramaarogya.Screens.maps

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.gramaarogya.Models.MapState

class MapsViewModel: ViewModel() {
    var state by mutableStateOf(MapState())
}