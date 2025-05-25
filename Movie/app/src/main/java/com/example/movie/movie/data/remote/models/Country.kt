package com.example.movie.movie.data.remote.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Serializable
data class Country(
    @JsonNames("_id", "id")
    val id: String?,
    @SerialName("name")
    val name: String?,
    @SerialName("slug")
    val slug: String?
)