package com.example.movie.movie.data.remote.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Episode(
    @SerialName("server_data")
    val serverData: List<ServerData?>?,
    @SerialName("server_name")
    val serverName: String?
)