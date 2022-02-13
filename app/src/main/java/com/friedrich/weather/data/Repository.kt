package com.friedrich.weather.data

import com.friedrich.weather.data.model.city.CityData
import com.friedrich.weather.data.model.weather.CityWeatherData
import retrofit2.Call

class Repository(private val retrofitService: RetrofitService) {

    fun fetchCitiesByName(cityName: String): Call<List<CityData>> =
        retrofitService.fetchCitiesByName(cityName)

    fun fetchWeatherByCoords(lat: Double, lon: Double): Call<CityWeatherData> =
        retrofitService.fetchWeatherByCoords(lat.toString(), lon.toString())

}