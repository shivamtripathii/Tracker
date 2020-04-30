package com.noprofit.tracker19.Models.IndiaCountry


import com.google.gson.annotations.SerializedName

data class IndiaCountry(
    val active: Int,
    val cases: Int,
    val casesPerOneMillion: Int,
    val continent: String,
    val country: String,
    val countryInfo: CountryInfo,
    val critical: Int,
    val deaths: Int,
    val deathsPerOneMillion: Int,
    val recovered: Int,
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