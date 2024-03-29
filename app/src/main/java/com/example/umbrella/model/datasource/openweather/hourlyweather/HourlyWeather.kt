package com.example.umbrella.model.datasource.openweather.hourlyweather


import com.google.gson.annotations.SerializedName

data class HourlyWeather(
    @SerializedName("city")
    val city: City,
    @SerializedName("cnt")
    val cnt: Int,
    @SerializedName("cod")
    val cod: String,
    @SerializedName("list")
    val list: List<X>,
    @SerializedName("message")
    val message: Double
)