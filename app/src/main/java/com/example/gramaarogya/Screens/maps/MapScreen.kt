package com.example.gramaarogya.Screens.maps

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings

@Composable
@Preview
fun MapScreen(
    viewModel: MapsViewModel = MapsViewModel()
) {
    val uiSettings = remember { MapUiSettings(zoomControlsEnabled = false) }
//    Scaffold (
//        containerColor = bgWhite,
//        modifier = Modifier.fillMaxSize()
//    ) { innerPadding ->
        GoogleMap(
            modifier = Modifier
                .fillMaxSize(),
            properties = viewModel.state.properties,
            uiSettings = MapUiSettings(zoomControlsEnabled = false)
        )

    //}
}