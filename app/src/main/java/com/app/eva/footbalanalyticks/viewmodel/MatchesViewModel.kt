package com.app.eva.footbalanalyticks.viewmodel


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.eva.footbalanalyticks.model.Match
import com.app.eva.footbalanalyticks.model.MatchesResponse
import com.app.eva.footbalanalyticks.repo.MatchesRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MatchesViewModel : ViewModel() {
    private val repository = MatchesRepository()
    private val _matches = MutableLiveData<List<Match>>()
    val matches: LiveData<List<Match>> get() = _matches

    fun fetchMatches() {
        repository.getMatches(object : Callback<MatchesResponse> {
            override fun onResponse(call: Call<MatchesResponse>, response: Response<MatchesResponse>) {
                if (response.isSuccessful) {
                    val matchesResponse = response.body()
                    matchesResponse?.let {
                        _matches.value = it.matches
                    }
                } else {
                    Log.e("MatchesViewModel", "Failed to fetch matches: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<MatchesResponse>, t: Throwable) {
                Log.e("MatchesViewModel", "Error fetching matches: ${t.message}")
            }
        })
    }
}