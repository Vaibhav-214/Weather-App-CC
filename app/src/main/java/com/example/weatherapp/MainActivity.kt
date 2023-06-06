package com.example.weatherapp

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.widget.ToggleButton
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.weatherapp.data.repoImpl.WeatherRepoImpl
import com.example.weatherapp.presentation.WeatherViewModel
import com.example.weatherapp.presentation.ui.ThemeSwitcher
import com.example.weatherapp.presentation.ui.WeatherCard
import com.example.weatherapp.presentation.ui.WeatherForecast
import com.example.weatherapp.ui.theme.DarkBlue
import com.example.weatherapp.ui.theme.DeepBlue
import com.example.weatherapp.ui.theme.WeatherAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel : WeatherViewModel by viewModels()

    private lateinit var permissionLauncher : ActivityResultLauncher<String>
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) {
            viewModel.loadWeatherInfo()
        }

        permissionLauncher.launch(
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        setContent {

            val themeState by remember {
                viewModel.lightTheme
            }.collectAsState()
            WeatherAppTheme (darkTheme = !themeState){

                val state by remember {
                    viewModel.state
                }.collectAsState()
                // A surface container using the 'background' color from the theme
                Box ( modifier = Modifier.fillMaxSize()){
                    Column (modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.primary),
                        horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                        WeatherCard(viewModel = viewModel, backgroundColor = MaterialTheme.colorScheme.secondary)

                        Spacer(modifier = Modifier.height(16.dp))

                        WeatherForecast(viewModel = viewModel)

                        Spacer(modifier = Modifier.height(32.dp))

                        state.weatherInfo?.currentWeatherData?.let {
                            ThemeSwitcher(
                                darkTheme = !themeState,
                                onClick = {viewModel.setLightTheme(!themeState)}
                            )
                        }
                    }
                    if(state.isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                    state.error?.let { error ->
                        Text(
                            text = error,
                            color = Color.Red,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
            }
        }
    }
}

