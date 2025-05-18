package com.example.movie.ui.repository

import com.example.movie.common.ApiMapper
import com.example.movie.movie.data.remote.api.MovieApiService
import com.example.movie.movie.data.remote.models.MovieDetailDto
import com.example.movie.movie.domain.model.MovieDetail

class MovieDetailRepository(val apiService: MovieApiService,val apiMapper: ApiMapper<MovieDetail, MovieDetailDto>) {



    suspend fun fetchMovieDetailBySlug(slug:String):MovieDetail?{
        try{
            //     4.1.16 Hệ thống thực hiện callApi của https://www.kkphim.vip qua phuơng thức getMovieBySlug(slug)
            val movieDetailDto = apiService.getMovieBySlug(slug)
            return apiMapper.mapToDomain(movieDetailDto)
        } catch (e:Exception){
            e.printStackTrace()
        }

        return null
    }
}