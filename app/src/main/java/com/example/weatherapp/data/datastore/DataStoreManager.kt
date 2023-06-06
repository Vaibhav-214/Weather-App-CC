package com.example.weatherapp.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.map
import javax.inject.Inject

val Context.datastore by preferencesDataStore(name = "weather_datastore")
class DataStoreManager @Inject constructor(
    @ApplicationContext val context: Context
) {

    val LIGHT_THEME_KEY = booleanPreferencesKey(name = "theme_key")

    // internally the value will be stored for key "theme_key" and we will access it in our code using
    // LIGHT_THEME_KEY

    // There are multiple preference key available for primitive data types
    //like intPreferenceKey, floatPreferenceKey, stringPreferenceKey

    suspend fun saveThemeKey(lightTheme: Boolean) {
        // as edit function is what helps us perform asynchronous operation here we need to make our saveThemeKey suspend
        // function to use the edit function
        context.datastore.edit { mutablePreference ->
            mutablePreference[LIGHT_THEME_KEY] = lightTheme
        }
    }

    // now to get the value we will use this here .data will give us flow of all
    // the pereferences we will map the one we need
    val lightTheme = context.datastore.data.map { preference ->
        preference[LIGHT_THEME_KEY] ?: false
    }
}