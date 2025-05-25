package com.example.movie.movie.data.remote.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BreadCrumb(
    @SerialName("isCurrent")
    val isCurrent: Boolean?,
    @SerialName("name")
    val name: String?,
    @SerialName("position")
    val position: Int?
)