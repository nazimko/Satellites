package com.mhmtn.satellites

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mhmtn.satellites.ui.theme.SatellitesTheme
import com.mhmtn.satellites.view.DetailScreen
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

                    composable("detail_screen/{satelliteId}/{satelliteName}", arguments = listOf(
                        navArgument("satelliteId"){
                            type = NavType.IntType
                        },
                        navArgument("satelliteName"){
                            type = NavType.StringType
                        }
                    )){

                        val satelliteId = remember {
                            it.arguments?.getInt("satelliteId")
                        }

                        val satelliteName = remember {
                            it.arguments?.getString("satelliteName")
                        }

                        DetailScreen(id = satelliteId ?: 1, name = satelliteName ?: "")
                    }

                }
            }
        }
    }
}
