package com.noprofit.tracker19.Models.IndianStates


import com.google.gson.annotations.SerializedName

data class Data(
    val regional: List<Regional>,
    val summary: Summary,
    @SerializedName("unofficial-summary")
    val unofficialSummary: List<UnofficialSummary>
)