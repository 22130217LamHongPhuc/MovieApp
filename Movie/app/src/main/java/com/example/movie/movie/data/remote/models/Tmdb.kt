package com.example.movie.movie.data.remote.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Tmdb(
    @SerialName("id")
    val id: String?,
    @SerialName("season")
    val season: Int?,
    @SerialName("type")
    val type: String?,
    @SerialName("vote_average")
    val voteAverage: Double?,
    @SerialName("vote_count")
    val voteCount: Int?
)