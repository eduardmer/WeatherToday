package com.weatherapp.domain.use_case

import com.weatherapp.common.Resource
import com.weatherapp.domain.model.Weather
import com.weatherapp.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetWeatherUseCase @Inject constructor(
    private val repository: WeatherRepository
) {

    operator fun invoke(cityName: String): Flow<Resource<Weather>> =
        repository.getWeather(cityName).map { result ->
            when (result) {
                is Resource.Loading -> Resource.Loading<Weather>()
                is Resource.Error -> Resource.Error(result.error!!, null)
                is Resource.Success<Weather> -> Resource.Success(result.data!!)
            }
        }

}