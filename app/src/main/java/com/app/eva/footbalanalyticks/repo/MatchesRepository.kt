package com.app.eva.footbalanalyticks.repo

import com.app.eva.footbalanalyticks.model.ApiService2
import com.app.eva.footbalanalyticks.model.MatchesResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MatchesRepository {
    private val apiService = ApiService2.create()

    fun getMatches(callback: Callback<MatchesResponse>) {
        val call = apiService.getMatches()
        call.enqueue(callback)
    }
}