package com.weatherapp.data.remote

import com.weatherapp.BuildConfig
import com.weatherapp.data.remote.dto.WeatherDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("weather")
    suspend fun getWeather(
        @Query("q") cityName: String,
        @Query("appid") appId: String = BuildConfig.APP_ID
    ): Response<WeatherDto>

}