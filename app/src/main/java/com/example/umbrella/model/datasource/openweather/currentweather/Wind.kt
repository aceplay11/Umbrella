package com.example.umbrella.model.datasource.openweather.currentweather


import com.google.gson.annotations.SerializedName

data class Wind(
    @SerializedName("deg")
    val deg: Float,
    @SerializedName("speed")
    val speed: Double
)