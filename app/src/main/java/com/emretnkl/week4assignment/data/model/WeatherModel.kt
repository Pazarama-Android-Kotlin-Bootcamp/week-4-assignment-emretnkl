package com.emretnkl.week4assignment.data.model


import com.google.gson.annotations.SerializedName

data class WeatherModel(
    @SerializedName("lat")
    val lat: Double?,
    @SerializedName("lon")
    val lon: Double?,
    @SerializedName("timezone")
    val timezone: String?,
    @SerializedName("timezone_offset")
    val timezoneOffset: Int?,
    @SerializedName("current")
    val current: Current?,
    @SerializedName("minutely")
    val minutely: List<Minutely>?,
    @SerializedName("hourly")
    val hourly: List<Hourly>?,
    @SerializedName("daily")
    val daily: List<Daily>?
)