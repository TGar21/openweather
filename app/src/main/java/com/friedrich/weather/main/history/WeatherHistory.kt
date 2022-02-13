package com.friedrich.weather.main.history

import android.content.Context
import com.friedrich.weather.sharedprefs.CustomSharedPrefs
import com.google.gson.Gson

/**
 * Handles saving and receiving of weather history records from shared preferences.
 */
class WeatherHistory {

    fun saveResults(context: Context, city: String, temperature: String, timestamp: Long) {
        val oldRecords = getRecordsFromSharedPrefs(context)
        val newRecord = WeatherRecord(city, temperature, timestamp)
        // API sometimes returns the result twice and we don't want duplicate records.
        if (oldRecords.items.isNotEmpty() && oldRecords.items.last().equalsWithWholeHours(newRecord)) {
            return
        }
        CustomSharedPrefs(context).weatherRecords = Gson().toJson(
            updateRecords(oldRecords, newRecord)
        )
    }

    fun getRecordsFromSharedPrefs(context: Context): WeatherRecordWrapper {
        val jsonRecords = CustomSharedPrefs(context).weatherRecords
        return if (jsonRecords.isNullOrEmpty()) {
            WeatherRecordWrapper(mutableListOf())
        } else {
            Gson().fromJson(jsonRecords, WeatherRecordWrapper::class.java)
        }
    }

    private fun updateRecords(
        records: WeatherRecordWrapper,
        newRecord: WeatherRecord
    ): WeatherRecordWrapper {
        if (records.items.isNotEmpty() && records.items.size >= 10) records.items.removeAt(0)
        records.items.add(newRecord)
        return records
    }
}
