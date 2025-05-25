package com.example.movie.movie.data.remote.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Data(
    @SerialName("APP_DOMAIN_CDN_IMAGE")
    val aPPDOMAINCDNIMAGE: String?,

    val breadCrumb: List<BreadCrumb>?,
    @SerialName("items")
    val items: List<Item>?,


)