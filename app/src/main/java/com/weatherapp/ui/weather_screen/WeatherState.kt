package com.weatherapp.ui.weather_screen

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.weatherapp.R
import com.weatherapp.common.DateTimeUtils
import com.weatherapp.common.NetworkError
import com.weatherapp.domain.model.Weather
import com.weatherapp.ui.utils.toCelsius

data class WeatherState(
    @DrawableRes
    val backgroundImage: Int = R.drawable.bg_day,
    val cityName: String = "",
    val name: String = "",
    val temp: String = "",
    val iconUrl: String? = null,
    val weatherDescription: String? = null,
    val weatherConditions: List<WeatherConditionsUi> = emptyList(),
    val error: NetworkError? = null,
    val isLoading: Boolean = false
)

data class WeatherConditionsUi(
    @StringRes
    val titleId: Int,
    @DrawableRes
    val iconId: Int,
    val description: String,
    val additionalDatas: String
)

fun Weather.getWeatherConditions(): List<WeatherConditionsUi> {
    val weatherConditions = listOf<WeatherConditionsUi>(
        WeatherConditionsUi(
            titleId = R.string.sunrise,
            iconId = R.drawable.ic_sunrise,
            description = DateTimeUtils.getDate(sunrise, DateTimeUtils.TIME_FORMAT_24H),
            additionalDatas = "Sunset: ${DateTimeUtils.getDate(sunset.toLong(), DateTimeUtils.TIME_FORMAT_24H)}"
        ),
        WeatherConditionsUi(
            titleId = R.string.wind,
            iconId = R.drawable.ic_wind,
            description = windSpeed.toString(),
            additionalDatas = "Degree: $windDegree"
        ),
        WeatherConditionsUi(
            titleId = R.string.humidity,
            iconId = R.drawable.ic_humidity,
            description = humidity.toString(),
            additionalDatas = ""
        ),
        WeatherConditionsUi(
            titleId = R.string.rainfall,
            iconId = R.drawable.ic_rain,
            description = "$rainFall in the last 24 hours",
            additionalDatas = ""
        ),
        WeatherConditionsUi(
            titleId = R.string.feels_like,
            iconId = R.drawable.ic_thermometer,
            description = feelsLike.toCelsius(),
            additionalDatas = ""
        ),
        WeatherConditionsUi(
            titleId = R.string.pressure,
            iconId = R.drawable.ic_pressure,
            description = pressure.toString(),
            additionalDatas = ""
        )
    )
    return weatherConditions
}
