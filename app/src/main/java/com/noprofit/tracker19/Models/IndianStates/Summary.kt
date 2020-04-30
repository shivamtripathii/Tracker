package com.noprofit.tracker19.Models.IndianStates


data class Summary(
    val confirmedButLocationUnidentified: Int,
    val confirmedCasesForeign: Int,
    val confirmedCasesIndian: Int,
    val deaths: Int,
    val discharged: Int,
    val total: Int
)