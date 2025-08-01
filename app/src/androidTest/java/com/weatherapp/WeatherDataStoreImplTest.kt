package com.weatherapp

import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import com.weatherapp.data.local.WeatherPreferencesSerializer
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.io.File

class WeatherDataStoreImplTest {

    private lateinit var testDataStore: DataStore<WeatherPreferences>
    private lateinit var tempFile: File
    private val city = "Tirana"

    @Before
    fun setup() {
        tempFile = File.createTempFile("weather_test", ".pb")
        testDataStore = DataStoreFactory.create(
            serializer = WeatherPreferencesSerializer(),
            produceFile = { tempFile }
        )
    }

    @Test
    fun writeAndReadWeatherProto() = runTest {
        val weather = WeatherPreferences.newBuilder()
            .setName(city)
            .setTemp(0.0)
            .setIconUrl("")
            .setDescription("")
            .setSunrise(0)
            .setSunset(0)
            .setWindSpeed(0.0)
            .setWindDegree(0)
            .setHumidity(0)
            .setRainFall(0)
            .setFeelsLike(0.0)
            .setPressure(0)
            .build()

        testDataStore.updateData { weather }
        val actualProto = testDataStore.data.first()
        assertEquals(city, actualProto.name)
    }

    @After
    fun tearDown() {
        tempFile.delete()
    }

}