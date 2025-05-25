package com.example.movie.dao

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.movie.movie.domain.model.Movie

@Database(entities = [Movie::class], version = 1)
abstract class AppDatabase : RoomDatabase() {


    abstract fun MovieHistoryDao(): MovieDAO


}