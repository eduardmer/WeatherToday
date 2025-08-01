package com.weatherapp

import com.weatherapp.common.Resource
import com.weatherapp.data.repository.WeatherRepositoryImpl
import com.weatherapp.domain.model.Weather
import com.weatherapp.domain.use_case.GetWeatherUseCase
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class GetWeatherUseCaseTest {

    private lateinit var api: FakeWeatherApi
    private lateinit var repository: WeatherRepositoryImpl
    private lateinit var getWeatherUseCase: GetWeatherUseCase

    @Before
    fun setup() {
        api = FakeWeatherApi()
        repository = WeatherRepositoryImpl(api, FakeWeatherDataStore())
        getWeatherUseCase = GetWeatherUseCase(repository)
    }

    @Test
    fun `returns success when api return success`() = runTest {
        api.weatherShouldThrow = false

        val emissions = mutableListOf<Resource<Weather>>()
        getWeatherUseCase(FakeData.city).collect {
            emissions.add(it)
        }

        val result = (emissions.last() as? Resource.Success)?.data
        assertEquals(FakeData.city, result?.name)
    }

    @Test
    fun `returns loading when flow emits loading`() = runTest {
        val emissions = mutableListOf<Resource<Weather>>()
        getWeatherUseCase(FakeData.city).collect {
            emissions.add(it)
        }
        assertTrue(emissions.first() is Resource.Loading)
    }

    @Test
    fun `returns error when api returns error`() = runTest {
        api.weatherShouldThrow = true
        val emissions = mutableListOf<Resource<Weather>>()
        getWeatherUseCase(FakeData.city).collect {
            emissions.add(it)
        }
        assertTrue(emissions.last() is Resource.Error)
    }

}