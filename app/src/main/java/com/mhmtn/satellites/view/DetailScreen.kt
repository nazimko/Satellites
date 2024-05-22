package com.mhmtn.satellites.view

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mhmtn.satellites.model.Position
import com.mhmtn.satellites.model.Posses
import com.mhmtn.satellites.model.SatelliteDetail
import com.mhmtn.satellites.ui.theme.Gray80
import com.mhmtn.satellites.util.Resource
import com.mhmtn.satellites.viewmodel.SatelliteDetailViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.time.format.TextStyle

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun DetailScreen(
    id:Int,
    name:String,
    viewModel: SatelliteDetailViewModel = hiltViewModel()
) {

    var satelliteItem by remember {
        mutableStateOf<Resource<SatelliteDetail?>>(Resource.Loading())
    }

    var positionsItem by remember {
        mutableStateOf<Resource<Posses?>>(Resource.Loading())
    }

    LaunchedEffect(key1 = Unit) {
        satelliteItem = viewModel.getSatelliteDetail(id)
        positionsItem = viewModel.getSatellitePositions(id)
    }
    @Composable
    fun UpdatePosition(){
        val selectedPosition = positionsItem.data!!
        var position by remember {
            mutableStateOf<Position>(selectedPosition.positions[0])
        }
        val scope = rememberCoroutineScope()

        LaunchedEffect(key1 = Unit) {

            scope.launch {
                for (i in 0..2) {
                    position = selectedPosition.positions[i]
                    delay(3000)
                }
            }
        }
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column (
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                ){
                Text(text = "Last Position: ${position}")
            }
        }
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
                    Text(text = name, fontWeight = FontWeight.Bold, fontSize = 32.sp )
                    Text(text = selectedSatellite.first_flight, fontWeight = FontWeight.Thin)
                    Spacer(modifier = Modifier.height(48.dp))
                    Text(text = "Height/Mass:${selectedSatellite.height}/${selectedSatellite.mass.toString()}")
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = "Cost:${selectedSatellite.cost_per_launch}")
                    Spacer(modifier = Modifier.height(16.dp))
                    UpdatePosition()
                }

                is Resource.Loading -> {
                    CircularProgressIndicator()
                }

                is Resource.Error -> {
                    Text(text = satelliteItem.message!!)
                }
            }

        }
    }


}
