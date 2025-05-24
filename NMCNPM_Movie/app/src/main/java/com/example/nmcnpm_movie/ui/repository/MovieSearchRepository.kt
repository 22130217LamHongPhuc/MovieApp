package com.example.movie.ui.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.movie.common.ApiMapper
import com.example.movie.movie.data.remote.api.MovieApiService
import com.example.movie.movie.data.remote.models.MovieSearchDto
import com.example.movie.movie.data.remote.paging.MovieSearchPagingSource
import com.example.movie.movie.domain.model.MovieSearch
import kotlinx.coroutines.flow.Flow


class MovieSearchRepository(
    val apiService: MovieApiService,
    val apiMapper: ApiMapper<List<MovieSearch>, MovieSearchDto>
) {
    fun getMoviesSearchPager(keyword: String): Flow<PagingData<MovieSearch>> {
        Log.d("search", "query" + "2")
        // 8.1.14. khoi tao Pager()
        return Pager(
            config = PagingConfig(pageSize = 24, enablePlaceholders = false),
            pagingSourceFactory = {
                MovieSearchPagingSource(apiService, keyword, apiMapper)
            }
        ).flow
    }

}

