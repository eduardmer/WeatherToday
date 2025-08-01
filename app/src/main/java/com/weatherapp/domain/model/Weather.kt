package com.weatherapp.domain.model

data class Weather(
    val isDay: Boolean,
    val name: String,
    val temp: Double,
    val iconUrl: String,
    val description: String,
    val sunrise: Long = 0,
    val sunset: Long = 0,
    val windSpeed: Double = 0.0,
    val windDegree: Int = 0,
    val humidity: Int = 0,
    val rainFall: Int = 0,
    val feelsLike: Double = 0.0,
    val pressure: Int = 0,
)
