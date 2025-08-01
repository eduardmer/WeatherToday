package com.weatherapp.data.repository

import com.weatherapp.common.NetworkError
import com.weatherapp.common.Resource
import com.weatherapp.data.local.WeatherDataStore
import com.weatherapp.data.local.mapper.toDomainModel
import com.weatherapp.data.remote.WeatherApi
import com.weatherapp.data.remote.dto.toWeatherPreferences
import com.weatherapp.domain.model.Weather
import com.weatherapp.domain.repository.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import java.lang.Exception
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val api: WeatherApi,
    private val weatherDataStore: WeatherDataStore
) : WeatherRepository {

    override val weather: Flow<Weather?>
        get() = weatherDataStore.weather.map {
            if (it.name.isNullOrEmpty())
                null
            else it.toDomainModel()
        }

    override fun getWeather(cityName: String): Flow<Resource<Weather>> = flow {
        try {
            emit(Resource.Loading<Weather>())
            val response = api.getWeather(cityName = cityName)
            if (response.isSuccessful && response.body() != null) {
                val weather = response.body()!!.toWeatherPreferences()
                weatherDataStore.saveWeather(weather)
                emit(Resource.Success(weather.toDomainModel()))
            } else
                emit(Resource.Error<Weather>(
                    NetworkError.errorForHttpCode(response.code(), response.message()),
                ))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Resource.Error<Weather>(
                NetworkError.errorForHttpCode(-1)
            ))
        }
    }.flowOn(Dispatchers.IO)

}