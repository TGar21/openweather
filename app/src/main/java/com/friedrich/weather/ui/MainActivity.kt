package com.friedrich.weather.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.friedrich.weather.R
import com.friedrich.weather.data.BASE_URL
import com.friedrich.weather.data.Repository
import com.friedrich.weather.data.RetrofitService
import com.friedrich.weather.main.viewmodel.HistoryViewModel
import com.friedrich.weather.main.viewmodel.MainViewModel
import com.friedrich.weather.main.history.WeatherHistory
import com.friedrich.weather.ui.UiStateStorageMainScreen.mutableCityName
import com.friedrich.weather.ui.UiStateStorageMainScreen.mutableNearestRecord
import com.friedrich.weather.ui.UiStateStorageMainScreen.mutableTemperature
import com.friedrich.weather.ui.UiStateStorageMainScreen.mutableWeatherDesc
import com.friedrich.weather.ui.screen.HistoryWeatherScreen
import com.friedrich.weather.ui.screen.MainWeatherScreen

const val MAIN_SCREEN_ID = "mainWeather"
const val HISTORY_SCREEN_ID = "historyWeather"

class MainActivity : ComponentActivity() {

    private val mainViewModel by lazy {
        val retrofitService = RetrofitService.getInstance(BASE_URL)
        MainViewModel(Repository(retrofitService))
    }
    private val historyViewModel = HistoryViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavHost(navController, startDestination = "mainWeather") {
                composable(MAIN_SCREEN_ID) { MainWeatherScreen(this@MainActivity, mainViewModel, navController) }
                composable(HISTORY_SCREEN_ID) { HistoryWeatherScreen(this@MainActivity, historyViewModel) }
            }
        }

        mainViewModel.weatherData.observe(this) { weatherData ->
            if (weatherData == null) {
                mutableNearestRecord.value = getString(R.string.city_not_found)
                mutableTemperature.value = ""
                mutableWeatherDesc.value = ""
            } else {
                mutableNearestRecord.value = getString(R.string.city_name, mutableCityName.value, weatherData.name, weatherData.sys.country)
                mutableTemperature.value = weatherData.main.temp.toString()
                mutableWeatherDesc.value = weatherData.weather.joinToString(", ") { it.description }
                WeatherHistory().saveResults(this, "${mutableCityName.value} (${weatherData.name}):", weatherData.main.temp.toString(), System.currentTimeMillis())
            }
        }
    }
}