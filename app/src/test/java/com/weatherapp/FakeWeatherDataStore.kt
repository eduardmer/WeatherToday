package com.weatherapp

import com.weatherapp.data.local.WeatherDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeWeatherDataStore : WeatherDataStore {

    var fakeWeather: WeatherPreferences = WeatherPreferences.getDefaultInstance()

    override val weather: Flow<WeatherPreferences>
        get() = flow {
            emit(fakeWeather)
        }

    override suspend fun saveWeather(weather: WeatherPreferences) {
        fakeWeather = weather
    }

}