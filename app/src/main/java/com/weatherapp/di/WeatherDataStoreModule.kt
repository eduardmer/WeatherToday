package com.weatherapp.di

import com.weatherapp.data.local.WeatherDataStore
import com.weatherapp.data.local.data_store.WeatherDataStoreImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class WeatherDataStoreModule {

    @Binds
    @Singleton
    abstract fun bindWeatherDataSource(
        weatherDataSourceImpl: WeatherDataStoreImpl
    ): WeatherDataStore

}