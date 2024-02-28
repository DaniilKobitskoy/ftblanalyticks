package com.app.eva.footbalanalyticks.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

import retrofit2.http.Header
import retrofit2.http.Path

interface FootballDataApi {
    @GET("v4/competitions/{competitionId}/standings")
    fun getStandings(
        @Path("competitionId") competitionId: String,
        @Header("X-Auth-Token") authToken: String = "10513829e69748ec8d312067cf8983cd"
    ): Call<StandingsResponse>
}
