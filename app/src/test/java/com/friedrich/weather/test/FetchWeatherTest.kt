package com.friedrich.weather.test

import com.friedrich.weather.data.Repository
import com.friedrich.weather.data.RetrofitService
import com.friedrich.weather.data.model.city.CityData
import com.friedrich.weather.main.viewmodel.MainViewModel
import com.friedrich.weather.test.util.JsonResponseReader
import com.google.gson.Gson
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.json.JSONObject
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner
import java.net.HttpURLConnection

const val CITY_NAME = "London"
const val LAT = -122.08
const val LON = 37.39

@RunWith(RobolectricTestRunner::class)
class FetchWeatherTest {

    private lateinit var repository: Repository
    private lateinit var mainViewModel: MainViewModel
    private lateinit var mockWebServer: MockWebServer

    @Test
    fun successFileExists() {
        val reader = JsonResponseReader("success_response.json")
        assertNotNull(reader.content)
    }

    @Test
    fun fetchCityAndCheckResponseCode200Returned() {
        val reader = JsonResponseReader("success_response.json")
        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(reader.content)
        mockWebServer.enqueue(response)
        val actualResponse = repository.fetchCitiesByName(CITY_NAME).execute()
        assertEquals(
            response.toString().contains("200"),
            actualResponse.code().toString().contains("200")
        )
    }

    @Test
    fun fetchWeatherAndCheckResponseCode200Returned() {
        val reader = JsonResponseReader("success_response.json")
        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(reader.content)
        mockWebServer.enqueue(response)
        val actualResponse = repository.fetchWeatherByCoords(LAT, LON).execute()
        assertEquals(
            response.toString().contains("200"),
            actualResponse.code().toString().contains("200")
        )
    }

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        mockWebServer = MockWebServer()
        mockWebServer.start()
        val url = mockWebServer.url("/").toString();
        val repository = Repository(RetrofitService.getInstance(url))
        mainViewModel = MainViewModel(repository)
        this.repository = repository
    }

}