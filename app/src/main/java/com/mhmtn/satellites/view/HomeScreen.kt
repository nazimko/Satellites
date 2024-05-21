package com.mhmtn.satellites.view

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mhmtn.satellites.model.Satellites
import com.mhmtn.satellites.viewmodel.SatellitesViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: SatellitesViewModel = hiltViewModel()
) {

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.White),
        contentAlignment = Alignment.TopCenter){

        SatelliteList(navController = navController)

    }
}


@Composable
fun SatelliteList(navController: NavController, viewModel: SatellitesViewModel = hiltViewModel()) {

    val satelliteList by remember {
        viewModel.satellitesList
    }

    val errorMessage by remember {
        viewModel.errorMessage
    }

    val isLoading by remember {
        viewModel.isLoading
    }

    SatellitesListView(satellites = satelliteList, navController= navController)

    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()){

        if (isLoading){
            CircularProgressIndicator()
        }

        if (errorMessage.isNotEmpty()) {
            Text(text = "Error." )
        }
    }
}

@Composable
fun SatellitesListView(satellites: List<Satellites>, navController: NavController) {

    LazyColumn (contentPadding = PaddingValues(5.dp)) {
        items(satellites){
            SatelliteRow(navController = navController, satellite =  it)
            Divider(color = Color.Black)
        }
    }
}


@Composable
fun SatelliteRow(navController: NavController, satellite : Satellites) {

    Row (horizontalArrangement = Arrangement.SpaceEvenly,
         modifier = Modifier
             .background(color = Color.White)
             .fillMaxWidth()
             .clickable {
                 //detailscreen
             }) {
        Canvas(modifier = Modifier.fillMaxHeight(0.5f).padding(20.dp)) {
            drawCircle(
                color = if (satellite.active) {Color.Green} else Color.Red ,
                radius = 40f
            )
        }

        Column {
            Text(text = satellite.name)
            Spacer(modifier = Modifier.padding(4.dp))
            Text(text = if (satellite.active){ "Active" } else "Passive")
        }
        Spacer(modifier = Modifier.height(10.dp))
    }
}