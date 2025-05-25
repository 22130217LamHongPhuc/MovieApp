package com.example.movie.movie.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_history")
data class Movie (@PrimaryKey val id:String, val name:String, val slug:String, val thumbUrl:String, val episodeCurrent:String, val poster:String,val timestamp: Long = System.currentTimeMillis() )