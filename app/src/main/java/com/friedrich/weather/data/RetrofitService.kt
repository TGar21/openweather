package com.friedrich.weather.data

import com.friedrich.weather.data.model.city.CityData
import com.friedrich.weather.data.model.weather.CityWeatherData
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


const val API_KEY = "98fbedf6b05d5ac745fa4a68c2aa2b7b"
const val BASE_URL = "http://api.openweathermap.org"
const val QUERY_COORDINATES_BY_CITY = "/geo/1.0/direct?limit=1&appid=$API_KEY"
const val QUERY_WEATHER_BY_COORDS = "/data/2.5/weather?units=metric&appid=$API_KEY"

/**
 * Interface for retrieving data from server.
 */
interface RetrofitService {
    @GET(QUERY_COORDINATES_BY_CITY)
    fun fetchCitiesByName(
        @Query("q") name: String
    ): Call<List<CityData>>


    @GET(QUERY_WEATHER_BY_COORDS)
    fun fetchWeatherByCoords(
        @Query("lat") lat: String,
        @Query("lon") lon: String
    ): Call<CityWeatherData>

    companion object {
        fun getInstance(baseUrl: String): RetrofitService {
            return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(baseUrl)
                .build()
                .create(RetrofitService::class.java)
        }
    }
}