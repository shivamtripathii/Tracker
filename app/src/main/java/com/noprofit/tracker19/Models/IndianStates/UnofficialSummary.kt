package com.noprofit.tracker19.Models.IndianStates


data class UnofficialSummary(
    val active: Int,
    val deaths: Int,
    val recovered: Int,
    val source: String,
    val total: Int
)