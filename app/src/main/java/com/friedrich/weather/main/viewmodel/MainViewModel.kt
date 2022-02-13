package com.friedrich.weather.main.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.friedrich.weather.data.Repository
import com.friedrich.weather.data.model.city.CityData
import com.friedrich.weather.data.model.weather.CityWeatherData
import com.friedrich.weather.ui.UiStateStorageMainScreen
import com.friedrich.weather.util.NetworkUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * View model for main functionality e.g. searching weather by city.
 */
class MainViewModel(private val repository: Repository) : ViewModel() {
    val city = MutableLiveData<CityData>()
    val cityErrorMessage = MutableLiveData<String>()
    val weatherData = MutableLiveData<CityWeatherData?>()
    val weatherErrorMessage = MutableLiveData<String>()
    lateinit var cityDataObserver: Observer<CityData>

    private fun fetchCity(name: String) {
        val response = repository.fetchCitiesByName(name)
        response.enqueue(object : Callback<List<CityData>> {
            override fun onResponse(
                call: Call<List<CityData>>,
                response: Response<List<CityData>>
            ) {
                val allCities = response.body()
                city.postValue(allCities?.firstOrNull())
            }

            override fun onFailure(call: Call<List<CityData>>, t: Throwable) {
                cityErrorMessage.postValue(t.message)
            }
        })
    }

    fun fetchTemperatureByCityName(cityName: String) {
        fetchCity(cityName)
        cityDataObserver = Observer { cityData ->
            UiStateStorageMainScreen.mutableInProgress.value = false
            if (cityData == null) {
                weatherData.postValue(null)
            } else {
                fetchWeather(cityData.lat, cityData.lon)
            }
        }
        city.observeForever(cityDataObserver)
    }

    private fun fetchWeather(lat: Double, lon: Double) {
        val response = repository.fetchWeatherByCoords(lat, lon)
        response.enqueue(object : Callback<CityWeatherData> {
            override fun onResponse(
                call: Call<CityWeatherData>,
                response: Response<CityWeatherData>
            ) {
                weatherData.postValue(response.body())
            }

            override fun onFailure(call: Call<CityWeatherData>, t: Throwable) {
                weatherErrorMessage.postValue(t.message)
            }
        })
    }

    override fun onCleared() {
        city.removeObserver(cityDataObserver)
        super.onCleared()
    }

    fun isInternetOn(context: Context): Boolean {
        return NetworkUtil().isConnected(context)
    }
}