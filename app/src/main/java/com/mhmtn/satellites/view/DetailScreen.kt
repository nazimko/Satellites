package com.mhmtn.satellites.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mhmtn.satellites.model.SatelliteDetail
import com.mhmtn.satellites.ui.theme.Gray80
import com.mhmtn.satellites.util.Resource
import com.mhmtn.satellites.viewmodel.SatelliteDetailViewModel

@Composable
fun DetailScreen(
    id:Int,
    name:String,
    viewModel: SatelliteDetailViewModel = hiltViewModel()
) {

    var satelliteItem by remember {
        mutableStateOf<Resource<SatelliteDetail?>>(Resource.Loading())
    }

    LaunchedEffect(key1 = Unit) {
        satelliteItem = viewModel.getSatelliteDetail(id)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Gray80),
        contentAlignment = Alignment.Center
    ){

        Column( verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .background(color = Gray80)) {

            when (satelliteItem){
                is Resource.Success -> {
                    val selectedSatellite = satelliteItem.data!!
                    Text(text = name, fontWeight = FontWeight.Bold)
                    Text(text = selectedSatellite.first_flight)
                    Spacer(modifier = Modifier.height(32.dp))
                    Text(text = selectedSatellite.mass.toString())
                }

                is Resource.Loading -> {

                }

                is Resource.Error -> {

                }
            }

        }
    }
}