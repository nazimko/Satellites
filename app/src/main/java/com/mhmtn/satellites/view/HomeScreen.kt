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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mhmtn.satellites.model.Satellites
import com.mhmtn.satellites.ui.theme.Gray80
import com.mhmtn.satellites.viewmodel.SatellitesViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: SatellitesViewModel = hiltViewModel()
) {

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Gray80),
        contentAlignment = Alignment.TopCenter){

        Column (verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
            ) {
            SearchBar(modifier = Modifier.background(color=Gray80).padding(16.dp)){
                viewModel.searchCryptoList(it)
            }
            Spacer(modifier = Modifier.height(16.dp))
            SatelliteList(navController = navController)
        }
        
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(modifier: Modifier = Modifier,
              onSearch : (String) -> Unit = {}
) {
    var text = remember { mutableStateOf("") }

    Box (modifier = modifier) {

        TextField(value = text.value, onValueChange = {
            text.value=it
        },
            keyboardActions = KeyboardActions(onDone = {onSearch(text.value)}),
            maxLines = 1,
            singleLine = true,
            textStyle = TextStyle(color = MaterialTheme.colorScheme.tertiary),
            shape = RoundedCornerShape(12.dp),
            placeholder = { Text(text = "Search") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 6.dp)
                .background(color = Color.White, CircleShape),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                disabledContainerColor = Color.White
            ),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Search Icon"
                )
            }
        )
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
        verticalAlignment = Alignment.CenterVertically,
         modifier = Modifier
             .background(color = Gray80)
             .fillMaxWidth()
             .padding(4.dp)
             .clickable {
                 //detailscreen
             }) {
        Canvas(modifier = Modifier
            .fillMaxHeight(0.5f)
            .padding(16.dp)) {
            drawCircle(
                color = if (satellite.active) {Color.Green} else Color.Red ,
                radius = 40f
            )
        }

        Column {
            Text(text = satellite.name, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.padding(2.dp))
            Text(text = if (satellite.active){ "Active" } else "Passive")
        }
        Spacer(modifier = Modifier.height(10.dp))
    }
}