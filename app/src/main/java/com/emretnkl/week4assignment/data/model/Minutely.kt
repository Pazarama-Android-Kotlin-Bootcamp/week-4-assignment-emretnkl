package com.emretnkl.week4assignment.data.model


import com.google.gson.annotations.SerializedName

data class Minutely(
    @SerializedName("dt")
    val dt: Long?,
    @SerializedName("precipitation")
    val precipitation: Double?
)