package com.emretnkl.week4assignment.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.emretnkl.week4assignment.R
import com.emretnkl.week4assignment.data.api.ApiClient
import com.emretnkl.week4assignment.data.model.WeatherModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class WeatherFragment : Fragment() {

    private lateinit var tvCityName : TextView
    private lateinit var tvWeatherDegree : TextView
    private lateinit var tvFirstDayName : TextView
    private lateinit var tvFirstDayMaxDegree : TextView
    private lateinit var tvFirstDayMinDegree : TextView
    private lateinit var tvSecondDayName : TextView
    private lateinit var tvSecondDayMaxDegree : TextView
    private lateinit var tvSecondDayMinDegree : TextView
    private lateinit var tvThirdDayName : TextView
    private lateinit var tvThirdDayMaxDegree : TextView
    private lateinit var tvThirdDayMinDegree : TextView
    private lateinit var tvHumidity : TextView
    private lateinit var tvWindSpeed : TextView
    private lateinit var ivCurrentWeatherIcon : ImageView
    private lateinit var ivFirstDayIcon : ImageView
    private lateinit var ivSecondDayIcon : ImageView
    private lateinit var ivThirdDayIcon : ImageView

    val degreeSign = "°"
    val percentageSign = "%"
    val kmh = " km/h"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_weather, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // View items have been defined.
        tvCityName = view.findViewById(R.id.tvCityName)
        tvWeatherDegree = view.findViewById(R.id.tvWeatherDegree)
        tvFirstDayName = view.findViewById(R.id.tvFirstDayName)
        tvFirstDayMaxDegree = view.findViewById(R.id.tvFirstDayMaxDegree)
        tvFirstDayMinDegree = view.findViewById(R.id.tvFirstDayMinDegree)
        tvSecondDayName = view.findViewById(R.id.tvSecondDayName)
        tvSecondDayMaxDegree = view.findViewById(R.id.tvSecondDayMaxDegree)
        tvSecondDayMinDegree = view.findViewById(R.id.tvSecondDayMinDegree)
        tvThirdDayName = view.findViewById(R.id.tvThirdDayName)
        tvThirdDayMaxDegree = view.findViewById(R.id.tvThirdDayMaxDegree)
        tvThirdDayMinDegree = view.findViewById(R.id.tvThirdDayMinDegree)
        tvHumidity = view.findViewById(R.id.tvHumidity)
        tvWindSpeed = view.findViewById(R.id.tvWindSpeed)
        ivCurrentWeatherIcon = view.findViewById(R.id.ivCurrentWeatherIcon)
        ivFirstDayIcon = view.findViewById(R.id.ivFirstDayIcon)
        ivSecondDayIcon = view.findViewById(R.id.ivSecondDayIcon)
        ivThirdDayIcon = view.findViewById(R.id.ivThirdDayIcon)

        getOneCall()
    }

    // The timestamp data is formatted and the day information is obtained.
    fun getDayOfWeek(timestamp: Long): String {
        return SimpleDateFormat("EEEE", Locale.ENGLISH).format(timestamp * 1000)
    }

    // Received meter/second wind speed data has been converted to kilometer/hours.
    fun convertMeterSecondToKmh(ms: Double) : Double {
        return ms * 36 / 10
    }

    // Request method to get all weather data for the stated location.
    private fun getOneCall() {
        ApiClient.getApiService().getOneCall().enqueue(object : Callback<WeatherModel>{
            override fun onResponse(call: Call<WeatherModel>, response: Response<WeatherModel>) {

                Log.d("onResponse Log : ",response.body().toString())

                if (response.isSuccessful){

                    // Weather icon Url's are getting prepared.
                    val currentWeatherIconUrl = "https://openweathermap.org/img/wn/" + response.body()?.current?.weather?.get(0)?.icon.toString() + "@2x.png"
                    val firstDayIconUrl = "https://openweathermap.org/img/wn/" + response.body()?.daily?.get(1)?.weather?.get(0)?.icon.toString() + "@2x.png"
                    val secondDayIconUrl = "https://openweathermap.org/img/wn/" + response.body()?.daily?.get(2)?.weather?.get(0)?.icon.toString() + "@2x.png"
                    val thirdDayIconUrl = "https://openweathermap.org/img/wn/" + response.body()?.daily?.get(3)?.weather?.get(0)?.icon.toString() + "@2x.png"

                    // Current weather informations are getting binded with views.
                    tvCityName.text = response.body()?.timezone.toString()
                    tvWeatherDegree.text = response.body()?.current?.temp?.toInt().toString() + degreeSign
                    tvHumidity.text = response.body()?.current?.humidity.toString() + percentageSign
                    //tvWindSpeed.text = response.body()?.current?.windSpeed.toString() + kmh
                    tvWindSpeed.text = response.body()?.current?.windSpeed?.let { convertMeterSecondToKmh(it) }?.toInt().toString() + kmh
                    Glide.with(this@WeatherFragment).load(currentWeatherIconUrl).into(ivCurrentWeatherIcon)

                    // Day names are getting binded with views.
                    tvFirstDayName.text = response.body()?.daily?.get(1)?.dt?.let { getDayOfWeek(it) }
                    tvSecondDayName.text = response.body()?.daily?.get(2)?.dt?.let { getDayOfWeek(it) }
                    tvThirdDayName.text = response.body()?.daily?.get(3)?.dt?.let { getDayOfWeek(it) }

                    // Maximum temperatures are getting binded with views.
                    tvFirstDayMaxDegree.text = response.body()?.daily?.get(1)?.temp?.max?.toInt().toString() + degreeSign
                    tvSecondDayMaxDegree.text = response.body()?.daily?.get(2)?.temp?.max?.toInt().toString() + degreeSign
                    tvThirdDayMaxDegree.text = response.body()?.daily?.get(3)?.temp?.max?.toInt().toString() + degreeSign

                    // Minimum temperatures are getting binded with views.
                    tvFirstDayMinDegree.text = response.body()?.daily?.get(1)?.temp?.min?.toInt().toString() + degreeSign
                    tvSecondDayMinDegree.text = response.body()?.daily?.get(2)?.temp?.min?.toInt().toString() + degreeSign
                    tvThirdDayMinDegree.text = response.body()?.daily?.get(3)?.temp?.min?.toInt().toString() + degreeSign

                    // Weather icons are getting binded with views.
                    Glide.with(this@WeatherFragment).load(firstDayIconUrl).into(ivFirstDayIcon)
                    Glide.with(this@WeatherFragment).load(secondDayIconUrl).into(ivSecondDayIcon)
                    Glide.with(this@WeatherFragment).load(thirdDayIconUrl).into(ivThirdDayIcon)


                }

            }

            override fun onFailure(call: Call<WeatherModel>, t: Throwable) {
                Log.d("onFailure Log : ",t.toString())
            }

        })
    }

}