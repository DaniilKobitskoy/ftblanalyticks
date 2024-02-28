package com.app.eva.footbalanalyticks.model

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    val instance: FootballDataApi by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.football-data.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(FootballDataApi::class.java)
    }
}

