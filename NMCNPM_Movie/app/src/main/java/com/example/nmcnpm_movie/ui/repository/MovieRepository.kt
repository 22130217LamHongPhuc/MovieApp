package com.example.movie.ui.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.movie.common.ApiMapper
import com.example.movie.movie.data.remote.api.MovieApiService
import com.example.movie.movie.data.remote.models.MovieDto
import com.example.movie.movie.data.remote.models.MovieSearchDto
import com.example.movie.movie.data.remote.paging.MovieCompileType
import com.example.movie.movie.domain.model.Movie
import com.example.movie.movie.domain.model.MovieSearch
import kotlinx.coroutines.flow.Flow

class MovieRepository(val apiMapper: ApiMapper<List<Movie>, MovieDto>,
                      val apiService:MovieApiService,
                      val apiMapperCategory: ApiMapper<List<MovieSearch>,MovieSearchDto>) {


    suspend fun fetchHomeMovie():List<Movie>{
        try {
            val movieDto = apiService.getMovieDataMovie(1)

            return apiMapper.mapToDomain(movieDto)
        }catch (e:Exception){
            Log.d("ssss",e.message.toString())
        }

        return listOf()

    }


    suspend fun fetchHomeTypeList(category: String,size:Int = 6): List<MovieSearch> {
        try {
            //     4.1.6 Hệ thống thực hiện call api của https://www.kkphim.vip qua phuơng thức getMovieByTypeList()

            val movieDto = apiService.getMovieByTypeList(category,size)
            Log.d("comp",movieDto.data?.items?.size.toString() ?: "null")


            return apiMapperCategory.mapToDomain(movieDto)
        }catch (e:Exception){
            Log.d("ssss",e.message.toString())
        }

        return emptyList();


    }

    suspend fun fetchTypeListPaging(typeList: String,size:Int = 6): Flow<PagingData<MovieSearch>> {
        return Pager(
            config = PagingConfig(pageSize = 24, enablePlaceholders = false),
            pagingSourceFactory = {
                MovieCompileType(apiService,typeList,apiMapperCategory)
            }
        ).flow


    }




}