package com.example.movie.movie.data.remote.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Pagination(
    @SerialName("currentPage")
    val currentPage: Int?,
    @SerialName("totalItems")
    val totalItems: Int?,
    @SerialName("totalItemsPerPage")
    val totalItemsPerPage: Int?,
    @SerialName("totalPages")
    val totalPages: Int?,

)