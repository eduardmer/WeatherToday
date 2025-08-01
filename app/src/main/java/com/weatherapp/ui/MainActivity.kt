package com.weatherapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.weatherapp.ui.theme.WeatherAppTheme
import com.weatherapp.ui.weather_screen.WeatherScreen
import com.weatherapp.ui.weather_screen.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeatherAppTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.WeatherScreen.route,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        composable(route = Screen.WeatherScreen.route) {
                            val viewModel: WeatherViewModel by viewModels()
                            WeatherScreen(viewModel.state.collectAsStateWithLifecycle().value, viewModel::onEvent)
                        }
                    }
                }
            }
        }
    }
}