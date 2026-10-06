package com.example.gramaarogya.Screens.maps

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.gramaarogya.BuildConfig
import com.example.gramaarogya.Models.Clinics
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.CircularBounds
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.SearchNearbyRequest
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerComposable
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState

private val NABHA_DEFAULT = LatLng(30.3745, 76.1517)

private fun hasAnyLocationPermission(context: Context): Boolean = ContextCompat.checkSelfPermission(
    context, Manifest.permission.ACCESS_FINE_LOCATION
) == PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
    context, Manifest.permission.ACCESS_COARSE_LOCATION
) == PackageManager.PERMISSION_GRANTED

private fun fetchLocation(
    context: Context,
    client: FusedLocationProviderClient,
    onLocation: (LatLng) -> Unit
) {
    val hasFine = ContextCompat.checkSelfPermission(
        context, Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED
    val hasCoarse = ContextCompat.checkSelfPermission(
        context, Manifest.permission.ACCESS_COARSE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED
    if (!hasFine && !hasCoarse) return

    client.lastLocation.addOnSuccessListener { location ->
        if (location != null) {
            onLocation(LatLng(location.latitude, location.longitude))
        } else {
            client.getCurrentLocation(
                Priority.PRIORITY_BALANCED_POWER_ACCURACY,
                CancellationTokenSource().token
            ).addOnSuccessListener { fresh ->
                fresh?.let { onLocation(LatLng(it.latitude, it.longitude)) }
            }
        }
    }
}

@Composable
@Preview
fun MapScreen(
    viewModel: MapsViewModel = MapsViewModel()
) {
    val context = LocalContext.current
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }

    if (BuildConfig.MAPS_API_KEY.isNotEmpty() && !Places.isInitialized()) {
        Places.initializeWithNewPlacesApiEnabled(
            context.applicationContext, BuildConfig.MAPS_API_KEY
        )
    }

    val placesClient =
        remember { if (Places.isInitialized()) Places.createClient(context) else null }
    var permissionGranted by remember { mutableStateOf(hasAnyLocationPermission(context)) }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { result -> permissionGranted = result.values.any { it } }

    LaunchedEffect(Unit) {
        if (!permissionGranted) {
            permissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }

    LaunchedEffect(permissionGranted) {
        if (permissionGranted) {
            fetchLocation(context, fusedLocationClient) { viewModel.setUserLocation(it) }
        }
    }


//Nabha as default
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(NABHA_DEFAULT, 12f)
    }

    LaunchedEffect(viewModel.state.userLocation) {
        viewModel.state.userLocation?.let { latLng ->
            cameraPositionState.position = CameraPosition.fromLatLngZoom(latLng, 14f)

            if (placesClient == null) {
                Log.e("MapScreen", "placesClient is null, Places not initialized (empty key?)")
                return@let
            }

            val placeFields = listOf(
                Place.Field.ID,
                Place.Field.DISPLAY_NAME,
                Place.Field.FORMATTED_ADDRESS,
                Place.Field.LOCATION
            )
            val circle = CircularBounds.newInstance(latLng, 5000.0)
            val request = SearchNearbyRequest.builder(circle, placeFields)
                .setIncludedTypes(listOf("hospital", "doctor"))
                .setMaxResultCount(20)
                .setRankPreference(SearchNearbyRequest.RankPreference.DISTANCE)
                .build()

            placesClient.searchNearby(request)
                .addOnSuccessListener { response ->
                    Log.d("MapScreen", "Found ${response.places.size} places")
                    val results = response.places.mapIndexedNotNull { index, place ->
                        val loc = place.location ?: return@mapIndexedNotNull null
                        Clinics(
                            id = index,
                            title = place.displayName ?: "Clinic",
                            address = place.formattedAddress ?: "",
                            phoneNo = 0L,
                            lat = loc.latitude,
                            lng = loc.longitude
                        )
                    }
                    viewModel.setNearbyClinics(results)
                }
                .addOnFailureListener { e -> Log.e("MapScreen", "Nearby search failed", e) }
        }
    }
    val mapProperties = remember(permissionGranted) {
        MapProperties(isMyLocationEnabled = permissionGranted)
    }
    val mapUiSettings = remember(permissionGranted) {
        MapUiSettings(zoomControlsEnabled = false, myLocationButtonEnabled = permissionGranted)
    }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        properties = mapProperties,
        uiSettings = mapUiSettings,
        cameraPositionState = cameraPositionState
    ) {
        viewModel.state.nearbyClinics.forEach { clinic ->
            MarkerComposable(
                clinic.id,
                state = rememberMarkerState(
                    key = "clinic_${clinic.id}",
                    position = LatLng(clinic.lat, clinic.lng)
                ),
                title = clinic.title,
                snippet = clinic.address,
                anchor = Offset(0.5f, 0.5f)
            ) {
                Box(
                    modifier = Modifier
                        .size(34.dp)
                        .background(Color(0xFFD32F2F), CircleShape)
                        .border(2.dp, Color.White, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.LocalHospital,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}