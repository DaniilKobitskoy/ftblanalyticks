package com.app.eva.footbalanalyticks.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.eva.footbalanalyticks.model.Standing
import com.app.eva.footbalanalyticks.model.StandingsResponse
import com.app.eva.footbalanalyticks.model.TeamStanding
import com.app.eva.footbalanalyticks.repo.StandingsRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StandingsViewModel : ViewModel() {
    private val repository = StandingsRepository()
    private val _standings = MutableLiveData<List<Standing>>()
    val standings: LiveData<List<Standing>> get() = _standings

    fun fetchStandings(competitionId: String) {
        repository.getStandings(competitionId, object : Callback<StandingsResponse> {
            override fun onResponse(call: Call<StandingsResponse>, response: Response<StandingsResponse>) {
                if (response.isSuccessful) {
                    val standingsResponse = response.body()
                    standingsResponse?.let {
                        _standings.value = standingsResponse?.standings

//                        _standings.value = it.standings.flatMap { standing ->
//                            standing.table.map { teamStanding ->
//                                Standing(
//                                    group = standing.group,
//                                    stage =  standing.stage,
//                                    type = standing.type,
//                                    table = standing.table.map { teamStanding ->
//                                    TeamStanding(
//                                        position = teamStanding.position,
//                                        team = teamStanding.team,
//                                        playedGames = teamStanding.playedGames,
//                                        form = teamStanding.form,
//                                        won = teamStanding.won,
//                                        draw = teamStanding.draw,
//                                        lost = teamStanding.lost,
//                                        points = teamStanding.points,
//                                        goalsFor = teamStanding.goalsFor,
//                                        goalsAgainst = teamStanding.goalsAgainst,
//                                        goalDifference = teamStanding.goalDifference
//                                    )
//                                }
//                                )
//
//                            }
//
//                        }
                    }
                } else {
                    Log.e("StandingsViewModel", "Failed to fetch standings: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<StandingsResponse>, t: Throwable) {
                Log.e("StandingsViewModel", "Error fetching standings: ${t.message}")
            }
        })
    }
}
