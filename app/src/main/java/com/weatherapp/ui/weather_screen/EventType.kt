package com.weatherapp.ui.weather_screen

sealed interface EventType {

    object DismissDialogEvent : EventType
    data class GetWeatherEvent(val cityName: String) : EventType

}