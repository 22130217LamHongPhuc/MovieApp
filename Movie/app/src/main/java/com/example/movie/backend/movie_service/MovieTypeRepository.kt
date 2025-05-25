package com.example.movie.backend.movie_service

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.movie.common.ApiMapper
import com.example.movie.movie.data.remote.api.MovieApiService
import com.example.movie.movie.data.remote.models.MovieSearchDto
import com.example.movie.movie.data.remote.paging.MovieTypePagingSource
import com.example.movie.movie.domain.model.MovieSearch
import kotlinx.coroutines.flow.Flow

class MovieTypeRepository(val apiService: MovieApiService,
                          val apiMapper: ApiMapper<List<MovieSearch>,MovieSearchDto>) {

    fun getMoviesCategorySearchPager(slug:String): Flow<PagingData<MovieSearch>> {
        Log.d("search","query"+"2")

        return Pager(
            config = PagingConfig(pageSize = 24, enablePlaceholders = false),
            pagingSourceFactory = {
                MovieTypePagingSource(apiService,"category",slug,apiMapper)
            }
        ).flow
    }

    fun getMoviesCountrySearchPager(slug:String): Flow<PagingData<MovieSearch>> {
        Log.d("search","query"+"2")

        return Pager(
            config = PagingConfig(pageSize = 24, enablePlaceholders = false),
            pagingSourceFactory = {
                MovieTypePagingSource(apiService,"country",slug,apiMapper)
            }
        ).flow
    }
}