package com.weatherapp.domain.repository

import com.weatherapp.common.Resource
import com.weatherapp.domain.model.Weather
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {

    val weather: Flow<Weather?>

    fun getWeather(cityName: String): Flow<Resource<Weather>>

}