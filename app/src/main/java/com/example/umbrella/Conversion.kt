package com.example.umbrella




//formula kelvin to celsius (x − 273.15)
fun convertToCelsius(temp: Double): String {
    val newTemp = (temp - 273.15)
    return "%.2f".format(newTemp)
}

// formula kelvin to fahrenheit(x − 273.15) × 9/5 + 32
fun convertToFahrenheit(temp: Double): String {
    val newTemp = ((temp - 273.15) * 9 / 5 + 32)
    return "%.2f".format(newTemp)

}