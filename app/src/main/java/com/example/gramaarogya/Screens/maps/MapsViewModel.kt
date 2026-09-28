package com.example.gramaarogya.Screens.maps

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.gramaarogya.Models.Clinics
import com.example.gramaarogya.Models.MapState
import com.google.android.gms.maps.model.LatLng

class MapsViewModel: ViewModel() {
    var state by mutableStateOf(MapState())
        private set
    fun setUserLocation(location : LatLng){
        state = state.copy(userLocation = location)
    }

    fun setNearbyClinics(list: List<Clinics>){
        state = state.copy(nearbyClinics = list)

    }
}