package com.noprofit.tracker19.Models.Countries

import com.google.gson.annotations.SerializedName


data class Countries(
    val active: Int,
    val activePerOneMillion: Double,
    val cases: Int,
    val casesPerOneMillion: Int,
    val continent: String,
    val country: String,
    val countryInfo: CountryInfo,
    val critical: Int,
    val criticalPerOneMillion: Int,
    val deaths: Int,
    val deathsPerOneMillion: Double,
    val population: Int,
    val recovered: Int,
    val recoveredPerOneMillion: Double,
    val tests: Int,
    val testsPerOneMillion: Int,
    val todayCases: Int,
    val todayDeaths: Int,
    val updated: Long
)

data class CountryInfo(
    val flag: String,
    @SerializedName("_id")
    val id: Int,
    val iso2: String,
    val iso3: String,
    val lat: Int,
    val long: Int
)