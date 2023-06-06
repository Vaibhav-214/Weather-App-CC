package com.example.weatherapp.di

import android.app.Application
import com.example.weatherapp.data.remote.WeatherApiService
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

const val BASE_URL = "https://api.open-meteo.com/"

@Module
@InstallIn(SingletonComponent::class)
class NetWorkModule {
//    @Provides
//    fun provideMoshi(): Moshi {
//        return Moshi.Builder()
//            .build()
//    }


    @Provides
    @Singleton
    fun provideRetrofit(
   //     okHttpClient: OkHttpClient
    ) : Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
           // .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

//    @Provides
//    fun provideOkHttpClient(): OkHttpClient {
//        return OkHttpClient.Builder()
//            .retryOnConnectionFailure(true)
//            .build()
//    }

    @Provides
    @Singleton
    fun provideWeatherApi(retrofit: Retrofit) : WeatherApiService  =
        retrofit.create(WeatherApiService::class.java)

    @Provides
    @Singleton
    fun provideFusedLocationProviderClient(appContext: Application) : FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(appContext)
    }
}

// we are using SingletonComponent because we want only single instance of
// some dependencies to be used throughout the app