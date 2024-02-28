package com.app.eva.footbalanalyticks.model

data class FiltersData(
    val filters: DataFilters,
    val resultSet: ResultSetData,
    val matches: List<MatchData>,
    val link: String?,
    val kinl: String,
    val t: String
)

data class DataFilters(
    val dateFrom: String,
    val dateTo: String,
    val permission: String
)

data class ResultSetData(
    val count: Int,
    val competitions: String,
    val first: String,
    val last: String,
    val played: Int
)

data class MatchData(
    val area: AreaData,
    val competition: CompetitionData,
    val season: SeasonData,
    val id: Int,
    val utcDate: String,
    val status: String,
    val matchday: Int,
    val stage: String,
    val group: String?,
    val lastUpdated: String,
    val homeTeam: TeamData,
    val awayTeam: TeamData,
    val score: ScoreData,
    val odds: OddsData,
    val referees: List<Any>
)

data class AreaData(
    val id: Int,
    val name: String,
    val code: String,
    val flag: String
)

data class CompetitionData(
    val id: Int,
    val name: String,
    val code: String,
    val type: String,
    val emblem: String
)

data class SeasonData(
    val id: Int,
    val startDate: String,
    val endDate: String,
    val currentMatchday: Int,
    val winner: String?
)

data class TeamData(
    val id: Int,
    val name: String,
    val shortName: String,
    val tla: String,
    val crest: String
)

data class ScoreData(
    val winner: String?,
    val duration: String,
    val fullTime: FullTimeData,
    val halfTime: HalfTimeData,
    val penalties: PenaltiesData?
)

data class FullTimeData(
    val home: Int?,
    val away: Int?
)

data class HalfTimeData(
    val home: Int?,
    val away: Int?
)

data class PenaltiesData(
    val home: Int?,
    val away: Int?
)

data class OddsData(
    val msg: String
)
