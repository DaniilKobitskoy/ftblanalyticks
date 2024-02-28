package com.app.eva.footbalanalyticks.model

import com.app.eva.footbalanalyticks.model.MatchesResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers

interface ApiService2 {
    @Headers("X-Auth-Token: 10513829e69748ec8d312067cf8983cd")
    @GET("matches")
    fun getMatches(): Call<MatchesResponse>

    companion object {
        private const val BASE_URL = "https://api.football-data.org/v4/"
        fun create(): ApiService2 {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(ApiService2::class.java)
        }
    }
}