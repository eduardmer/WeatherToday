package com.weatherapp.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.weatherapp.WeatherPreferences
import com.weatherapp.common.Constants
import com.weatherapp.data.local.WeatherPreferencesSerializer
import com.weatherapp.data.remote.WeatherApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideWeatherApi(): WeatherApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApi::class.java)
    }

    @Provides
    @Singleton
    fun provideUserPreferencesDataStore(
        @ApplicationContext context: Context,
        weatherPreferencesSerializer: WeatherPreferencesSerializer
    ): DataStore<WeatherPreferences> {
        return DataStoreFactory.create(serializer = weatherPreferencesSerializer) {
            context.dataStoreFile("weather_preferences.proto")
        }
    }

}