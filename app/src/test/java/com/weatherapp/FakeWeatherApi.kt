package com.weatherapp

import com.weatherapp.data.remote.WeatherApi
import com.weatherapp.data.remote.dto.WeatherDto
import retrofit2.Response
import java.io.IOException

class FakeWeatherApi(
    var weatherShouldThrow: Boolean = false,
) : WeatherApi {

    override suspend fun getWeather(cityName: String, appId: String): Response<WeatherDto> {
        if (weatherShouldThrow) throw IOException("Network error")
        return Response.success(FakeData.weatherDto)
    }

}