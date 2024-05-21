package com.mhmtn.satellites

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mhmtn.satellites.ui.theme.SatellitesTheme
import com.mhmtn.satellites.view.HomeScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SatellitesTheme {

                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "home_screen"){

                    composable("home_screen"){

                        HomeScreen(navController = navController)

                    }

                }
            }
        }
    }
}
