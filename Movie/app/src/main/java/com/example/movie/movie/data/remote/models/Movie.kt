package com.example.movie.movie.data.remote.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Movie(
    @SerialName("actor")
    val actor: List<String>?,
    @SerialName("category")
    val category: List<Category>?,
    @SerialName("chieurap")
    val chieurap: Boolean?,
    @SerialName("content")
    val content: String?,
    @SerialName("country")
    val country: List<Country>?,
    @SerialName("created")
    val created: Created?,
    @SerialName("director")
    val director: List<String>?,
    @SerialName("episode_current")
    val episodeCurrent: String?,
    @SerialName("episode_total")
    val episodeTotal: String?,
    @SerialName("_id")
    val id: String?,
    @SerialName("imdb")
    val imdb: Imdb?,
    @SerialName("is_copyright")
    val isCopyright: Boolean?,
    @SerialName("lang")
    val lang: String?,
    @SerialName("modified")
    val modified: Modified?,
    @SerialName("name")
    val name: String?,
    @SerialName("notify")
    val notify: String?,
    @SerialName("origin_name")
    val originName: String?,
    @SerialName("poster_url")
    val posterUrl: String?,
    @SerialName("quality")
    val quality: String?,
    @SerialName("showtimes")
    val showtimes: String?,
    @SerialName("slug")
    val slug: String?,
    @SerialName("status")
    val status: String?,
    @SerialName("sub_docquyen")
    val subDocquyen: Boolean?,
    @SerialName("thumb_url")
    val thumbUrl: String?,
    @SerialName("time")
    val time: String?,
    @SerialName("tmdb")
    val tmdb: Tmdb?,
    @SerialName("trailer_url")
    val trailerUrl: String?,
    @SerialName("type")
    val type: String?,
    @SerialName("view")
    val view: Int?,
    @SerialName("year")
    val year: Int?
)