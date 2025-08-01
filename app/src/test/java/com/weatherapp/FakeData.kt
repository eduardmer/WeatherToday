package com.weatherapp

import com.weatherapp.data.remote.dto.Main
import com.weatherapp.data.remote.dto.Sys
import com.weatherapp.data.remote.dto.Weather
import com.weatherapp.data.remote.dto.WeatherDto
import com.weatherapp.data.remote.dto.Wind

object FakeData {
    val city = "Tirana"

    val weatherDto = WeatherDto(
        200,
        Main(
            12.0,
            1,
            1,
            1,
            1,
            1.0,
            1.0,
            1.0,
        ),
        city,
        Sys(
            city,
            21424,
            231423
        ),
        listOf(
            Weather(
                "",
                "",
                1,
                ""
            )
        ),
        Wind(
            1,
            1.0,
            1.0
        )
    )

}