package com.example.movie.movie.data.remote.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Item(
    @SerialName("category")
    val category: List<Category?>?,
    @SerialName("country")
    val country: List<Country?>?,
    @SerialName("episode_current")
    val episodeCurrent: String?,
    @SerialName("_id")
    val id: String?,
    @SerialName("lang")
    val lang: String?,
    @SerialName("modified")
    val modified: Modified?,
    @SerialName("name")
    val name: String?,
    @SerialName("origin_name")
    val originName: String?,
    @SerialName("poster_url")
    val posterUrl: String?,
    @SerialName("quality")
    val quality: String?,
    @SerialName("slug")
    val slug: String?,
    @SerialName("sub_docquyen")
    val subDocquyen: Boolean?,
    @SerialName("thumb_url")
    val thumbUrl: String?,
    @SerialName("time")
    val time: String?,
    @SerialName("type")
    val type: String?,
    @SerialName("year")
    val year: Int?
)