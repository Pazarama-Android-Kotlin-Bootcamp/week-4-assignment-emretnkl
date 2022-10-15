package com.emretnkl.week4assignment.data.api

import com.emretnkl.week4assignment.data.model.WeatherModel
import com.emretnkl.week4assignment.data.utils.Constants
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET(Constants.ONECALL)
    fun getOneCall() : Call <WeatherModel>
}