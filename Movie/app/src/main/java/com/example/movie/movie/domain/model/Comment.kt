package com.example.movie.movie.domain.model

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class Comment(
    var idMovie:String ="",
    val userId: String = "",
    val userName: String = "",
    val content: String = "",
    val timestamp: Long = System.currentTimeMillis()
){
    fun formatDayCmt(): String {
           val now = System.currentTimeMillis()
            val diff = now - timestamp

            val seconds = diff / 1000
            val minutes = seconds / 60
            val hours = minutes / 60
            val days = hours / 24

            return when {
                seconds < 60 -> "Vừa xong"
                minutes < 60 -> "$minutes phút trước"
                hours < 24 -> "$hours giờ trước"
                days < 7 -> "$days ngày trước"
                else -> SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date(timestamp))

        }

    }

    constructor() : this("", "", "", "", 0)

}
