package com.example.movie.movie.data.remote.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDto(
    @SerialName("items")
    val items: List<Item?>?,
    @SerialName("pagination")
    val pagination: Pagination?,
    @SerialName("status")
    val status: Boolean?
)