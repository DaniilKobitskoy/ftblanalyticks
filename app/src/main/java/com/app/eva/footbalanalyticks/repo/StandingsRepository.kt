package com.app.eva.footbalanalyticks.repo

import com.app.eva.footbalanalyticks.model.RetrofitClient
import com.app.eva.footbalanalyticks.model.StandingsResponse
import retrofit2.Callback

class StandingsRepository {
    fun getStandings(competitionId: String, callback: Callback<StandingsResponse>) {
        val call = RetrofitClient.instance.getStandings(competitionId)
        call.enqueue(callback)
    }
}
