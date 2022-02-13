package com.friedrich.weather.main.history

import android.text.format.DateUtils
import kotlin.math.abs

data class WeatherRecord(
    val cityName: String,
    val temperature: String,
    val timestamp: Long
)

/**
 * Compares two [WeatherRecord] instances.
 *
 * The timestamps are counted as different if at least hour from each other.
 */
fun WeatherRecord.equalsWithWholeHours(other: WeatherRecord): Boolean {
    val diff = this.timestamp - other.timestamp
    return abs(diff) < DateUtils.HOUR_IN_MILLIS &&
            this.cityName == other.cityName &&
            this.temperature == other.temperature
}