package com.example.movie.movie.data.remote.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Params(
    @SerialName("filterCategory")
    val filterCategory: List<String?>?,
    @SerialName("filterCountry")
    val filterCountry: List<String?>?,
    @SerialName("filterType")
    val filterType: List<String?>?,
    @SerialName("filterYear")
    val filterYear: List<String?>?,
    @SerialName("keyword")
    val keyword: String?="",
    @SerialName("pagination")
    val pagination: Pagination?,
    @SerialName("sortField")
    val sortField: String?,
    @SerialName("sortType")
    val sortType: String?,
    @SerialName("type_slug")
    val typeSlug: String?
)