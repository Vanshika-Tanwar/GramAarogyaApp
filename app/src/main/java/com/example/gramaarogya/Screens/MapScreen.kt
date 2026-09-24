package com.example.gramaarogya.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.*
import com.example.gramaarogya.ui.theme.bgWhite
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
//        Box(
//            modifier = Modifier
//                .padding(innerPadding)
//                .background(color = bgWhite)
//        ) {}
        // comment out scaffold part and uncomment GoogleMap part to display
        GoogleMap(
            modifier = Modifier
                .fillMaxSize(),
            properties = viewModel.state.properties,
            uiSettings = MapUiSettings(zoomControlsEnabled = false)
        )

    //}
}