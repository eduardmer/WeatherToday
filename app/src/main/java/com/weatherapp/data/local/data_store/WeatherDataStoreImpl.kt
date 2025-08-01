package com.weatherapp.data.local.data_store

import androidx.datastore.core.DataStore
import com.weatherapp.WeatherPreferences
import com.weatherapp.data.local.WeatherDataStore
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherDataStoreImpl @Inject constructor(
    private val dataStore: DataStore<WeatherPreferences>
) : WeatherDataStore {

    override val weather: Flow<WeatherPreferences>
        get() = dataStore.data

    override suspend fun saveWeather(weather: WeatherPreferences) {
        dataStore.updateData {
            weather
        }
    }

}