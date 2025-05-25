package com.example.movie.backend.movie_service

import com.example.movie.dao.MovieDAO
import com.example.movie.movie.domain.model.Movie


class MovieHistoryRepository(val dao: MovieDAO) {
    fun getHistory() = dao.getAllHistory()
    suspend fun insert(item: Movie) = dao.insertHistory(item)
    suspend fun delete(slug: String) = dao.deleteBySlug(slug)
    suspend fun clear() = dao.clearHistory()

}