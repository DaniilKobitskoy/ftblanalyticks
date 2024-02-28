package com.app.eva.footbalanalyticks.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService3 {
    @GET("test_script.php")
    fun fetchData(@Query("c") c: String, @Query("ad") ad: String): Call<FiltersData>
}