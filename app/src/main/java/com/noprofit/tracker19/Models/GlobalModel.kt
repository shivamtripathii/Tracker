package com.noprofit.tracker19.Models


data class GlobalModel(
    val active: Int,
    val activePerOneMillion: Double,
    val affectedCountries: Int,
    val cases: Int,
    val casesPerOneMillion: Int,
    val critical: Int,
    val criticalPerOneMillion: Double,
    val deaths: Int,
    val deathsPerOneMillion: Double,
    val population: Long,
    val recovered: Int,
    val recoveredPerOneMillion: Double,
    val tests: Int,
    val testsPerOneMillion: Double,
    val todayCases: Int,
    val todayDeaths: Int,
    val updated: Long
)