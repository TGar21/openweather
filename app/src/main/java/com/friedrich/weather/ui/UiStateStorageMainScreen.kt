package com.friedrich.weather.ui

import androidx.compose.runtime.mutableStateOf

/**
 * Stores all data for main screen views.
 */
object UiStateStorageMainScreen {
    val mutableCityName = mutableStateOf("")
    val mutableNearestRecord = mutableStateOf("")
    val mutableTemperature = mutableStateOf("")
    val mutableWeatherDesc = mutableStateOf("")
    var mutableInProgress = mutableStateOf<Boolean>(false)
}