package com.weatherapp.domain.use_case

import com.weatherapp.domain.model.Weather
import com.weatherapp.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSavedWeatherUseCase @Inject constructor(
    private val repository: WeatherRepository
) {

    operator fun invoke(): Flow<Weather?> = repository.weather

}