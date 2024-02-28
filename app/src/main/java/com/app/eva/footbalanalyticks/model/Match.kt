package com.app.eva.footbalanalyticks.model


data class Match(
    val area: Area1,
    val competition: Competition1,
    val season: Season1,
    val id: Int,
    val utcDate: String,
    val status: String,
    val matchday: Int,
    val stage: String,
    val group: Any?,
    val lastUpdated: String,
    val homeTeam: Team1,
    val awayTeam: Team1,
    val score: Score,
    val odds: Odds,
    val referees: List<Any>
)

data class Area1(
    val id: Int,
    val name: String,
    val code: String,
    val flag: String
)

data class Competition1(
    val id: Int,
    val name: String,
    val code: String,
    val type: String,
    val emblem: String
)

data class Season1(
    val id: Int,
    val startDate: String,
    val endDate: String,
    val currentMatchday: Int,
    val winner: Any?
)

data class Team1(
    val id: Int,
    val name: String,
    val shortName: String,
    val tla: String,
    val crest: String
)

data class Score(
    val winner: Any?,
    val duration: String,
    val fullTime: ScoreDetail,
    val halfTime: ScoreDetail
)

data class ScoreDetail(
    val home: Int?,
    val away: Int?
)

data class Odds(
    val msg: String
)
data class MatchesResponse(
    val filters: Filters1,
    val resultSet: ResultSet,
    val matches: List<Match>
)

data class Filters1(
    val dateFrom: String,
    val dateTo: String,
    val permission: String
)

data class ResultSet(
    val count: Int,
    val competitions: String,
    val first: String,
    val last: String,
    val played: Int
)