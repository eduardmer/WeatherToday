package com.weatherapp.ui.weather_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weatherapp.R
import com.weatherapp.common.Resource
import com.weatherapp.domain.use_case.GetSavedWeatherUseCase
import com.weatherapp.domain.use_case.GetWeatherUseCase
import com.weatherapp.ui.utils.toCelsius
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val getWeatherUseCase: GetWeatherUseCase,
    private val getSavedWeatherUseCase: GetSavedWeatherUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(WeatherState())
    val state = _state.asStateFlow()
    private var job: Job? = null

    init {
        viewModelScope.launch {
            getSavedWeatherUseCase().collectLatest { weather ->
                weather?.let {
                    _state.emit(
                        WeatherState(
                            backgroundImage = if (it.isDay == true) R.drawable.bg_day else R.drawable.bg_night,
                            name = it.name,
                            temp = it.temp.toCelsius(),
                            iconUrl = it.iconUrl,
                            weatherDescription = it.description,
                            weatherConditions = it.getWeatherConditions(),
                            isLoading = false
                        )
                    )
                }
            }
        }
    }

    fun onEvent(event: EventType) {
        when (event) {
            EventType.DismissDialogEvent -> _state.value = state.value.copy(error = null)
            is EventType.GetWeatherEvent -> getWeather(event.cityName)
        }
    }

    private fun getWeather(cityName: String) {
        job?.cancel()
        job = viewModelScope.launch {
            getWeatherUseCase(cityName).collectLatest {
                when(it) {
                    is Resource.Success -> {
                        _state.emit(
                            state.value.copy(error = null, isLoading = false)
                        )
                    }
                    is Resource.Error -> {
                        _state.emit(
                            state.value.copy(
                                error = it.error,
                                isLoading = false
                            )
                        )
                    }
                    is Resource.Loading -> {
                        _state.emit(
                            state.value.copy(isLoading = true)
                        )
                    }
                }
            }
        }
    }

}