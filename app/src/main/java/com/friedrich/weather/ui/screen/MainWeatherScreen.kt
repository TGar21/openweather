package com.friedrich.weather.ui.screen

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.friedrich.weather.R
import com.friedrich.weather.data.BASE_URL
import com.friedrich.weather.data.Repository
import com.friedrich.weather.data.RetrofitService
import com.friedrich.weather.main.viewmodel.MainViewModel
import com.friedrich.weather.ui.HISTORY_SCREEN_ID
import com.friedrich.weather.ui.UiStateStorageMainScreen.mutableCityName
import com.friedrich.weather.ui.UiStateStorageMainScreen.mutableNearestRecord
import com.friedrich.weather.ui.UiStateStorageMainScreen.mutableInProgress
import com.friedrich.weather.ui.UiStateStorageMainScreen.mutableTemperature
import com.friedrich.weather.ui.UiStateStorageMainScreen.mutableWeatherDesc
import com.friedrich.weather.ui.theme.WeatherTheme


/**
 * Main screen: shows search bar with button and results of the search.
 */
@Composable
fun MainWeatherScreen(context: Context, viewModel: MainViewModel, navController: NavController) {
    val inProgress by mutableInProgress

    WeatherTheme {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            CityNameForm(viewModel, context)
            Spacer(modifier = Modifier.height(16.dp))
            if (inProgress) {
                CircularProgressIndicator()
            } else {
                CityWeatherView()
            }
            Spacer(modifier = Modifier.height(64.dp))
            Button(onClick = { navController.navigate(HISTORY_SCREEN_ID) }) {
                Text(text = context.getString(R.string.see_history))
            }
        }
    }
}

@Composable
fun CityWeatherView() {
    val cityName by mutableNearestRecord
    val temperature by mutableTemperature
    val description by mutableWeatherDesc
    Text(text = cityName, color = MaterialTheme.colors.secondary)
    if (temperature != "") Text(text = "$temperature°C", color = MaterialTheme.colors.secondary)
    Text(text = description, color = MaterialTheme.colors.secondary)
}

@Composable
fun CityNameForm(viewModel: MainViewModel, context: Context) { // todo comments and progress
    var input by rememberSaveable { mutableStateOf("") }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            value = input,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = MaterialTheme.colors.onBackground,
                textColor = MaterialTheme.colors.background
            ),
            onValueChange = {
                input = it
            },
            label = { Text("City name") }
        )
        Button(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f),
            onClick = {
                if (viewModel.isInternetOn(context)) {
                    mutableInProgress.value = true
                    mutableCityName.value = input
                    viewModel.fetchTemperatureByCityName(input)
                } else {
                    Toast.makeText(context, context.getString(R.string.no_internet), Toast.LENGTH_LONG).show()
                }
            }) {
            Text(text = "Search")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainWeatherPreview() {
    val navController = rememberNavController()
    val viewModel by lazy {
        val retrofitService = RetrofitService.getInstance(BASE_URL)
        MainViewModel(Repository(retrofitService))
    }
    WeatherTheme {
//        MainWeatherScreen(viewModel, navController)
    }
}
