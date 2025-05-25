package com.example.movie.movie.data.remote.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetailDto(
    @SerialName("episodes")
    val episodes: List<Episode>?,
    @SerialName("movie")
    val movie: Movie?,
    @SerialName("msg")
    val msg: String?,
    @SerialName("status")
    val status: Boolean?
)