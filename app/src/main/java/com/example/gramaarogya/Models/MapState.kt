package com.example.gramaarogya.Models

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.MapProperties

data class MapState(
    val properties: MapProperties = MapProperties(),
    val userLocation: LatLng? = null,
    val nearbyClinics: List<Clinics> = emptyList()
)
