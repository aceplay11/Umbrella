package com.example.umbrella.model.datasource.openweather.hourlyweather


import com.google.gson.annotations.SerializedName

data class Clouds(
    @SerializedName("all")
    val all: Int
)