package com.friedrich.weather.ui.screen

import android.content.Context
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.friedrich.weather.main.viewmodel.HistoryViewModel
import com.friedrich.weather.ui.theme.WeatherTheme

/**
 * History screen: shows last ten searches.
 */
@Composable
fun HistoryWeatherScreen(context: Context, viewModel: HistoryViewModel) {
    val text = viewModel.getHistory(context)
    Text(text = text, color = MaterialTheme.colors.secondary)
}


@Preview(showBackground = true)
@Composable
fun HistoryWeatherPreview() {
    WeatherTheme {
//        HistoryWeatherScreen()
    }
}