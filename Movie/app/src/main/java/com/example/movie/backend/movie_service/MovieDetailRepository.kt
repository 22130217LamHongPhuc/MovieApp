package com.example.movie.backend.movie_service

import com.example.movie.common.ApiMapper
import com.example.movie.movie.data.remote.api.MovieApiService
import com.example.movie.movie.data.remote.models.MovieDetailDto
import com.example.movie.movie.domain.model.MovieDetail

class MovieDetailRepository(val apiService: MovieApiService,val apiMapper: ApiMapper<MovieDetail, MovieDetailDto>) {



    suspend fun fetchMovieDetailBySlug(slug:String):MovieDetail?{
            //   4.1.6 Hệ thống thực hiện callApi của https://www.kkphim.vip qua phuơng thức getMovieBySlug(slug)
            val movieDetailDto = apiService.getMovieBySlug(slug)


        //   4.1.7 MovieDetailRepository nhận dữ liệu chi tiết phim trả về từ api
        return apiMapper.mapToDomain(movieDetailDto)

    }
}