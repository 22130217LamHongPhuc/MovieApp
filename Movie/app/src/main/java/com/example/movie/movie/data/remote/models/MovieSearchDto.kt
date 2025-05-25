package com.example.movie.movie.data.remote.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieSearchDto(
    @SerialName("data")
    val `data`: Data?,
    @SerialName("msg")
    val msg: String?,
    @SerialName("status")
    val status: String?
)