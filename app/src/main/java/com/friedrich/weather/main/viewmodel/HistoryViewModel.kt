package com.friedrich.weather.main.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.ViewModel
import com.friedrich.weather.R
import com.friedrich.weather.main.history.WeatherHistory
import java.sql.Date
import java.text.SimpleDateFormat

/**
 * View model for operations with history.
 *
 * Uses [WeatherHistory]
 */
class HistoryViewModel : ViewModel() {

    @SuppressLint("SimpleDateFormat")
    fun getHistory(context: Context): String {
        val records = WeatherHistory().getRecordsFromSharedPrefs(context)
        var history = ""
        if (records.items.isNullOrEmpty())
            history = context.getString(R.string.no_records)
        else {
            records.items.forEach { weatherRecord ->
                val date =
                    SimpleDateFormat("yyyy-MM-dd HH:mm").format(Date(weatherRecord.timestamp))
                history += "${weatherRecord.cityName} ($date): ${weatherRecord.temperature}°C\n"
            }
        }
        return history
    }
}