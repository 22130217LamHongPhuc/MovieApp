package com.example.movie.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movie.movie.domain.model.Movie
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistory(item: Movie)

    @Query("SELECT * FROM movie_history ORDER BY timestamp DESC")
    fun getAllHistory(): Flow<List<Movie>>

    @Query("DELETE FROM movie_history WHERE slug = :slug")
    suspend fun deleteBySlug(slug: String)

    @Query("DELETE FROM movie_history")
    suspend fun clearHistory()
}