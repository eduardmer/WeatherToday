package com.weatherapp

import com.weatherapp.common.Resource
import com.weatherapp.data.repository.WeatherRepositoryImpl
import com.weatherapp.domain.model.Weather
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class WeatherRepositoryImplTest {

    private lateinit var api: FakeWeatherApi
    private lateinit var repository: WeatherRepositoryImpl

    @Before
    fun setup() {
        api = FakeWeatherApi()
        repository = WeatherRepositoryImpl(api, FakeWeatherDataStore())
    }

    @Test
    fun `getCurrentWeather returns success`() = runTest {
        val result = repository.getWeather(FakeData.city).toList()
        assert(result[0] is Resource.Loading && (result[1] as? Resource.Success<Weather>)?.data?.name == FakeData.city)
    }

    @Test
    fun `getWeather returns error on exception`() = runTest {
        api.weatherShouldThrow = true
        val result = repository.getWeather(FakeData.city).toList()
        assert(result[0] is Resource.Loading && result[1] is Resource.Error<*>)
    }

}
