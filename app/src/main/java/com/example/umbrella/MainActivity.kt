package com.example.umbrella

import android.annotation.TargetApi
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color.TRANSPARENT
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.umbrella.model.datasource.openweather.currentweather.CurrentWeather
import com.example.umbrella.model.datasource.openweather.hourlyweather.HourlyWeather
import com.example.umbrella.model.datasource.openweather.hourlyweather.X
import com.example.umbrella.model.datasource.remote.BASE_URL
import com.example.umbrella.model.datasource.remote.WeatherApiService
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private val zipPrefString: String = "zipEntry"
    private lateinit var zipCode: String
    private var sharedPreferences: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //null check Shared Preferences
        //If null skip. If not set Shared Prefs
        sharedPreferences = getSharedPreferences(zipPrefString, Context.MODE_PRIVATE)
        val storedZip: String? = sharedPreferences?.getString(zipPrefString, null)
        if (!storedZip.isNullOrBlank()) {
            etUserInput.setText(storedZip)
            zipCode = storedZip.toString()
            getWeatherData(storedZip)
            etUserInput.setBackgroundColor(TRANSPARENT)
        } else {}
    }

    override fun onResume() {
        super.onResume()
        sharedPreferences = getSharedPreferences(zipPrefString, Context.MODE_PRIVATE)
        val storedZip: String? = sharedPreferences?.getString(zipPrefString, null)
        if (!storedZip.isNullOrBlank()) {
            etUserInput.setText(storedZip)
            zipCode = storedZip.toString()
            getWeatherData(storedZip)
            etUserInput.setBackgroundColor(TRANSPARENT)
        } else {
        }
    }
    fun onClick(view: View) =
        when (view.id) {
            R.id.btnSubmit -> {
                if (etUserInput.text.isNotBlank()) {
                    etUserInput.setBackgroundColor(TRANSPARENT)
                    zipCode = etUserInput.text.toString()
                    setSharedPreference(zipCode)
                    getWeatherData(zipCode)


                } else {
                    etUserInput.setBackgroundColor(resources.getColor(R.color.LTRED))
                    Toast.makeText(this, "Input field is empty!", Toast.LENGTH_SHORT).show()
                }


            }
            else -> {
            }
        }


    private fun getWeatherData(zipInput: String) {
        val retrofit = Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val response = retrofit.create(WeatherApiService::class.java)

        val callCurrent = response.currentEntry(zipInput)
        callCurrent.enqueue(object : Callback<CurrentWeather> {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            override fun onResponse(call: Call<CurrentWeather>, response: Response<CurrentWeather>) {
                val currentWeather = response.body()

                if (currentWeather?.name != null) {
                    tvCityName.text = "$zipCode: ${currentWeather?.name}"

                    var temp = currentWeather?.main?.temp
                    var tempChoice = radioGroup.checkedRadioButtonId

                    //Check for radio button checked
                    //Fahrenheit chosen
                    if (tempChoice == R.id.rbFahrenheit) {
                        //convert to Fahrenheit
                        var convertedTemp = convertToFahrenheit(temp!!.toDouble())
                        tvCurrentTemp.text = "$convertedTemp°F"
                        var compTemp = convertedTemp.toDouble()
                        //If below or above threshold change background to match
                        if (compTemp > 60) {
                            tvCurrentTemp.setBackgroundColor(resources.getColor(R.color.HOT))
                        } else if (compTemp < 60) {
                            tvCurrentTemp.setBackgroundColor(resources.getColor(R.color.COLD))
                        }
                    } else if (tempChoice == R.id.rbCelsius) {
                        var convertedTemp = convertToCelsius(temp!!.toDouble())
                        tvCurrentTemp.text = "$convertedTemp°C"
                        var compCTemp = convertedTemp.toDouble()
                        if (compCTemp > 15.56) {
                            tvCurrentTemp.setBackgroundColor(resources.getColor(R.color.HOT))
                        } else if (compCTemp < 15.56) {
                            tvCurrentTemp.setBackgroundColor(resources.getColor(R.color.COLD))
                        }

                    }
                }else{
                    etUserInput.setBackgroundColor(resources.getColor(R.color.LTRED))
                    Toast.makeText(this@MainActivity, "Invalid ZipCode! Enter New ZipCode", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<CurrentWeather>, t: Throwable) {
                Log.e("TAG", "onFailure Message: ${t.message}", t)
            }
        })

        val callHourly = response.hourlyEntry(zipInput)
        callHourly.enqueue(
            object : Callback<HourlyWeather> {
                override fun onResponse(call: Call<HourlyWeather>, response: Response<HourlyWeather>) {
                    val hourlyWeather = response.body()

                    var tempChoice = radioGroup.checkedRadioButtonId
                    if (hourlyWeather?.city?.name != null) {
                        val list: List<X>? = hourlyWeather?.list
                        rvHourlyView.layoutManager = LinearLayoutManager(applicationContext)
                        rvHourlyView.addItemDecoration(DividerItemDecoration(applicationContext, DividerItemDecoration.VERTICAL))
                        rvHourlyView.adapter = HourlyRecyclerViewAdapter(list, applicationContext,tempChoice )
                    }else{
                        etUserInput.setBackgroundColor(resources.getColor(R.color.LTRED))
                        Toast.makeText(this@MainActivity, "Invalid ZipCode! Enter New ZipCode", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<HourlyWeather>, t: Throwable) {
                    Log.e("TAG", "onFailure Message: ${t.message}", t)
                }
            })

    }

    private fun setSharedPreference(zipCode: String) {
        val editor = sharedPreferences?.edit()
        editor?.putString(zipPrefString, zipCode)?.apply()
        Toast.makeText(this, "Saved ZipCode: $zipCode", Toast.LENGTH_SHORT).show()

    }





}
