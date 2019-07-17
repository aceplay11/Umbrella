package com.example.umbrella.model.datasource.openweather.hourlyweather


import com.google.gson.annotations.SerializedName

data class Sys(
    @SerializedName("pod")
    val pod: String
)