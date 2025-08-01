package com.weatherapp.data.remote.dto

import com.weatherapp.WeatherPreferences
import com.weatherapp.common.Constants

data class WeatherDto(
    val cod: Int,
    val main: Main,
    val name: String,
    val sys: Sys,
    val weather: List<Weather>,
    val wind: Wind
)

fun WeatherDto.toWeatherPreferences(): WeatherPreferences {
    val weather = WeatherPreferences.newBuilder()
        .setName(name)
        .setTemp(main.temp)
        .setIconUrl(weather.firstOrNull()?.icon?.let { icon ->
            "${Constants.ICON_BASE_URL}$icon.png"
        }.orEmpty())
        .setDescription(weather.firstOrNull()?.description.orEmpty())
        .setSunrise(sys.sunrise.toLong() * 1000)
        .setSunset(sys.sunset.toLong() * 1000)
        .setWindSpeed(wind.speed)
        .setWindDegree(wind.deg)
        .setHumidity(main.humidity)
        .setRainFall(0)
        .setFeelsLike(main.feels_like)
        .setPressure(main.pressure)
        .build()
    return weather
}