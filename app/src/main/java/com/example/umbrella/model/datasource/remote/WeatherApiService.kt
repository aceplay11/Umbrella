package com.example.umbrella.model.datasource.remote


import com.example.umbrella.model.datasource.openweather.currentweather.CurrentWeather
import com.example.umbrella.model.datasource.openweather.hourlyweather.HourlyWeather
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


const val BASE_URL: String = "https://api.openweathermap.org/"
//http://api.openweathermap.org/data/2.5/forecast?zip=94040,us&appid=9842b2cbc3a469efc0ea67b40da2d3c7
const val HOUR_PATH: String = "data/2.5/forecast"
//http://api.openweathermap.org/data/2.5/weather?zip=94040,us&appid=9842b2cbc3a469efc0ea67b40da2d3c7
const val CURRENT_PATH: String = "data/2.5/weather"
const val API_KEY: String = "9842b2cbc3a469efc0ea67b40da2d3c7"


interface WeatherApiService {
    @GET(CURRENT_PATH)
    fun currentEntry(@Query("zip") zip: String, @Query("appid") api: String = API_KEY)
        : Call<CurrentWeather>

    @GET(HOUR_PATH)
    fun hourlyEntry(@Query("zip") zip: String, @Query("appid") api: String = API_KEY)
    : Call<HourlyWeather>


}