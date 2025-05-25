package com.example.movie.movie.data.remote.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Serializable
data class Category(
    @JsonNames("_id", "id") val id: String?,
    val name: String?,
    val slug: String?
)
