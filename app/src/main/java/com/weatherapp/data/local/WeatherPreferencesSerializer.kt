package com.weatherapp.data.local

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import com.weatherapp.WeatherPreferences
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherPreferencesSerializer @Inject constructor() : Serializer<WeatherPreferences> {

    override val defaultValue: WeatherPreferences
        get() = WeatherPreferences.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): WeatherPreferences {
        try {
            return WeatherPreferences.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(weatherPreferences: WeatherPreferences, output: OutputStream) =
        weatherPreferences.writeTo(output)

}