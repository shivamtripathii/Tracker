package com.noprofit.tracker19.Network

import com.noprofit.tracker19.Models.Countries.Countries
import com.noprofit.tracker19.Models.GlobalModel
import com.noprofit.tracker19.Models.IndiaCountry.IndiaCountry
import com.noprofit.tracker19.Models.IndianCity.IndianCity
import com.noprofit.tracker19.Models.IndianStates.IndianStates
import retrofit2.Call
import retrofit2.http.GET

interface Api {
    @GET("all")
    fun getGlobal() : Call<GlobalModel>

    @GET("countries/india")
    fun getIndia() : Call<IndiaCountry>

    @GET("countries")
    fun getCountries() : Call<List<Countries>>

    @GET("stats/latest")
    fun getIndianStates() : Call<IndianStates>

    @GET("/v2/state_district_wise.json")
    fun getIndianCites() : Call<ArrayList<IndianCity>>

    //https://api.covid19india.org/state_district_wise.json
}