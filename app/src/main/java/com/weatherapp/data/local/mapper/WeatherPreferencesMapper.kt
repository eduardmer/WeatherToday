package com.weatherapp.data.local.mapper

import com.weatherapp.WeatherPreferences
import com.weatherapp.domain.model.Weather

fun WeatherPreferences.toDomainModel() = Weather(
    isDay = System.currentTimeMillis() >= sunrise && System.currentTimeMillis() < sunset,
    name = name,
    temp = temp,
    iconUrl = iconUrl,
    description = description,
    sunrise = sunrise.toLong(),
    sunset = sunset.toLong(),
    windSpeed = windSpeed,
    windDegree = windDegree,
    humidity = humidity,
    rainFall = rainFall,
    feelsLike = feelsLike,
    pressure = pressure
)