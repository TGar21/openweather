package com.friedrich.weather.sharedprefs

import android.content.Context
import android.content.SharedPreferences

/**
 * Shared preferences access class. Helps to store simple data.
 */
class CustomSharedPrefs (context: Context) {

    private val preferences: SharedPreferences =
        context.getSharedPreferences(context.applicationContext.packageName, Context.MODE_PRIVATE)

    private val PREF_WEATHER_RECORDS = "prefWeatherRecords"

    var weatherRecords: String?
        get() = preferences.getString(PREF_WEATHER_RECORDS, "")
        set(value) = preferences.edit().putString(PREF_WEATHER_RECORDS, value).apply()
}