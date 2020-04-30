package com.noprofit.tracker19.Models.IndianCity


data class IndianCity(
    val districtData: List<DistrictData>,
    val state: String,
    val statecode: String
)

data class DistrictData(
    val active: Int,
    val confirmed: Int,
    val deceased: Int,
    val delta: Delta,
    val district: String,
    val notes: String,
    val recovered: Int
)

data class Delta(
    val confirmed: Int,
    val deceased: Int,
    val recovered: Int
)