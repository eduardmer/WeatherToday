package com.weatherapp.data.local

import com.weatherapp.WeatherPreferences
import kotlinx.coroutines.flow.Flow

interface WeatherDataStore {

    val weather: Flow<WeatherPreferences>

    suspend fun saveWeather(weather: WeatherPreferences)

}