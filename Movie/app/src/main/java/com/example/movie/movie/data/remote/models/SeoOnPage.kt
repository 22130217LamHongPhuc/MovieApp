package com.example.movie.movie.data.remote.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SeoOnPage(
    @SerialName("descriptionHead")
    val descriptionHead: String?,
    @SerialName("og_image")
    val ogImage: List<String?>?,
    @SerialName("og_type")
    val ogType: String?,
    @SerialName("og_url")
    val ogUrl: String?,
    @SerialName("titleHead")
    val titleHead: String?
)