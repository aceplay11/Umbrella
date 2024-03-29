package com.example.umbrella.model.datasource.openweather.hourlyweather


import com.google.gson.annotations.SerializedName

data class Main(
    @SerializedName("grnd_level")
    val grndLevel: Double,
    @SerializedName("humidity")
    val humidity: Int,
    @SerializedName("pressure")
    val pressure: Double,
    @SerializedName("sea_level")
    val seaLevel: Double,
    @SerializedName("temp")
    val temp: Double,
    @SerializedName("temp_kf")
    val tempKf: Double,
    @SerializedName("temp_max")
    val tempMax: Double,
    @SerializedName("temp_min")
    val tempMin: Double
)