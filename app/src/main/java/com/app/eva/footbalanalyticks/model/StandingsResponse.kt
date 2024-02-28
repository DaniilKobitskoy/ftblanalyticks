package com.app.eva.footbalanalyticks.model

data class StandingsResponse(
    val filters: Filters,
    val area: Area,
    val competition: Competition,
    val season: Season,
    val standings: List<Standing>
)

data class Filters(
    val season: String
)

data class Area(
    val id: Int,
    val name: String,
    val code: String,
    val flag: String
)

data class Competition(
    val id: Int,
    val name: String,
    val code: String,
    val type: String,
    val emblem: String
)

data class Season(
    val id: Int,
    val startDate: String,
    val endDate: String,
    val currentMatchday: Int,
    val winner: Any?
)

data class Standing(
    val stage: String,
    val type: String,
    val group: String,
    val table: List<TeamStanding>
)

data class TeamStanding(
    val position: Int,
    val team: Team,
    val playedGames: Int,
    val form: Any?,
    val won: Int,
    val draw: Int,
    val lost: Int,
    val points: Int,
    val goalsFor: Int,
    val goalsAgainst: Int,
    val goalDifference: Int
)

data class Team(
    val id: Int,
    val name: String,
    val shortName: String,
    val tla: String,
    val crest: String
)
