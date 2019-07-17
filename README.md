# Umbrella

Mobile NAT
For this code challenge, you will be creating a simple weather application named “Umbrella”. The application will download both the current conditions and an hour by hour forecast from Weather Underground. This application is intended to only be released in the United States. 

In this code challenge we will be paying particular attention to the following items:

	•	Design fidelity: Can you accurately and efficiently implement the design as specified in the art comps.
	•	Functionality: Does the application meet the technical requirements and work reliably?
	•	Architecture: How do you structure your application and its classes? Would the application be extensible? How do you encapsulate data parsing and access?
	•	Coding practices and use of IDE: How do you organize your files and groups? What practices do you adhere to make the code accessible and usable to other developers? How is source control used within the application?
	•	Fit and finish: Do you adhere to the platform’s recommended practices?
Getting Started
All assets to get started can be found at the git repository. Use the terminal or your favorite source control GUI to clone the appropriate repository using the credentials:

The checkout contains reference designs, design metrics, and classes to expedite development. Feel free to not use the classes that we provide, but they’re there to help you out.

Weather Underground’s API provides free access to their service for developers. You can sign up for an API key at http://www.wunderground.com/weather/api/
Functionality
Every time that the application becomes the foreground app, the application should fetch the weather. If the user has not entered a ZIP code previously, the application should automatically prompt the user for the ZIP code.
ZIP Code Entry
The user should be able to enter the ZIP code as prescribed by the designs. On Android, the user should also be able to switch between Imperial and Metric representation of the data. On iOS, the user should see the weather displayed in the phone’s current locale. If there is any error with the data, an alert should be presented with relevant information and the user should immediately taken to enter a ZIP code again. 
Hourly Weather Display
The hourly weather has two main sections. The top section is the current conditions of the entered ZIP code. If the temperature is below 60˚, use the cool color as specified by the designs. If the temperature is 60˚ or above, use the warm color as specified by the designs.

The other section of the main weather display is the hourly forecast. The data from the API should be grouped by days. The highest temperature of the day should have an warm tint as specified by the designs. The lowest temperature of the day should have a cool tint as specified by the designs. If there is a tie for the high or low, just highlight the first occurrence. If there is an occurrence where the high and low are the same hour, do not use a tint color.

Included in the checkout is a class that will provide the remote URL string for the weather icon - don’t use Weather Underground’s icons; they’re ugly.
Application Requirements
	•	The application should be built with the latest public SDK and can target the latest public release of the OS.
	•	iOS: Application can be written in either Swift or Objective-C. 
	•	Android: Application can be written in either Kotlin or Java.
Change Log
This is a living document. All changes that occur with will be detailed below. Most of the changes should be clarifications. If this document changes while you are completing the code challenge, you may continue with your assumptions or adapt to what has changed. If you choose to not implement what has changed, please detail that in a README.md at the root of your project.

	•	July 20, 2017: Added Kotlin as an allowed language
	•	March 16, 2017: Removed the requirement to change between Metric and Imperial Units for iOS
	•	October 13, 2015: Updated the document to allow the candidate to use Objective-C or Swift
	•	July 21, 2015: Updated the document to change “check in” to “locally commit” when referring to version control.
	•	February 5, 2015: Updated the document to ask for regular version control commits.



<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout
        xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:tools="http://schemas.android.com/tools"
        xmlns:app="http://schemas.android.com/apk/res-auto"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:background="#27BD86"
        tools:context=".MainActivity">

    <EditText
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:inputType="textPostalAddress"
            android:ems="10"
            android:id="@+id/etUserInput"
            app:layout_constraintStart_toStartOf="parent"
            android:layout_marginStart="8dp"
            android:layout_marginTop="8dp"
            app:layout_constraintTop_toTopOf="parent"
            android:hint="@string/enter_zipcode"
    />

    <Button
            android:text="@string/enter"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:id="@+id/btnSubmit"
            android:onClick="onClick"
            android:layout_marginTop="8dp"
            app:layout_constraintTop_toTopOf="parent"
            android:layout_marginStart="20dp"
            app:layout_constraintStart_toEndOf="@+id/etUserInput"/>

    <RadioGroup
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginTop="8dp"
            app:layout_constraintTop_toTopOf="parent"
            app:layout_constraintStart_toEndOf="@+id/btnSubmit"
            android:layout_marginStart="8dp"
            app:layout_constraintEnd_toEndOf="parent"
            android:layout_marginEnd="8dp"
            android:id="@+id/radioGroup">

        <RadioButton
                android:text="°F"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:id="@+id/rbFahrenheit"
                android:layout_weight="1" android:checked="true"/>

        <RadioButton
                android:text="°C"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:id="@+id/rbCelsius"
                android:layout_weight="1"/>

    </RadioGroup>
    <TextView
            style="@style/RVHeader"
            android:text="@string/city"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:id="@+id/tvCityName"
            app:layout_constraintEnd_toEndOf="parent"
            android:layout_marginEnd="8dp"
            app:layout_constraintStart_toStartOf="parent"
            android:layout_marginStart="8dp"
            android:layout_marginTop="28dp"
            app:layout_constraintTop_toBottomOf="@+id/etUserInput" app:layout_constraintHorizontal_bias="0.498"/>
    <TextView
            style="@style/RVHeader"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:textAlignment="center"
            android:id="@+id/tvCurrentTemp"
            app:layout_constraintStart_toStartOf="parent"
            android:layout_marginStart="8dp"
            android:layout_marginTop="8dp"
            app:layout_constraintTop_toBottomOf="@+id/tvCityName"
            android:inputType="number"/>
    <androidx.recyclerview.widget.RecyclerView
            android:layout_width="0dp"
            android:layout_height="0dp"
            android:id="@+id/rvHourlyView"
            android:layout_margin="8dp"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintBottom_toBottomOf="parent"

            app:layout_constraintTop_toBottomOf="@+id/textView"/>
    <TextView
            style="@style/RVHeader"
            android:text="@string/_5day_3hour_forecast"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:id="@+id/textView"
            app:layout_constraintEnd_toEndOf="parent"
            android:layout_marginEnd="8dp"
            app:layout_constraintStart_toStartOf="parent"
            android:layout_marginStart="8dp"
            app:layout_constraintTop_toBottomOf="@+id/tvCurrentTemp"
    />

</androidx.constraintlayout.widget.ConstraintLayout>

<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
              xmlns:app="http://schemas.android.com/apk/res-auto" xmlns:tools="http://schemas.android.com/tools"
              android:orientation="vertical"
              android:layout_width="match_parent"
              android:layout_height="wrap_content"
              android:background="#289284">

    <TextView
            style="@style/RVHeader"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:id="@+id/tvDate"
            android:layout_weight="1"/>
    <TextView
            style="@style/RVText"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:id="@+id/tvHourTemp"
            android:layout_weight="1"/>
    <TextView
            style="@style/RVText"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:id="@+id/tvDesc"
            android:layout_weight="1"/>
</LinearLayout>

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

package com.example.umbrella

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.umbrella.R.layout.hourly_recyclerview_items
import com.example.umbrella.model.datasource.openweather.hourlyweather.X
import kotlinx.android.synthetic.main.hourly_recyclerview_items.view.*

class HourlyRecyclerViewAdapter(private var weatherDetails: List<X>?, private val context: Context, conversion: Int) :
    RecyclerView.Adapter<ViewHolder>() {

    private var tempChoice = conversion
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(hourly_recyclerview_items, parent, false))
    }

    override fun getItemCount(): Int {
        return weatherDetails!!.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val temp = weatherDetails!![position].main.temp.toString()

        //Check for radio button checked
        //Fahrenheit chosen
        if (tempChoice == R.id.rbFahrenheit) {
            //convert to Fahrenheit
            val convertedTemp = convertToFahrenheit(temp.toDouble())
            holder.itemView.tvHourTemp.text = "Temperature: $convertedTemp°F"

        }else if (tempChoice == R.id.rbCelsius) {
            val convertedTemp = convertToCelsius(temp.toDouble())
            holder.itemView.tvHourTemp.text = "Temperature: $convertedTemp°C"
        }
        holder.itemView.tvDate.text = "Date: ${weatherDetails?.get(position)?.dtTxt}"
        holder.itemView.tvDesc.text = weatherDetails?.get(position)?.weather?.get(0)?.description

    }
    class ViewHolder (view: View) : RecyclerView.ViewHolder(view)
}

![device-2019-07-17-120013 edited](https://user-images.githubusercontent.com/51377336/61391844-9974cd00-a88b-11e9-91c3-ddade1b383f8.png)
