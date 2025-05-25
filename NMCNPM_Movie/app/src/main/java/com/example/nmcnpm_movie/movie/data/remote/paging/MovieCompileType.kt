package com.example.movie.movie.data.remote.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.movie.common.ApiMapper
import com.example.movie.movie.data.remote.api.MovieApiService
import com.example.movie.movie.data.remote.models.MovieSearchDto
import com.example.movie.movie.domain.model.MovieSearch

class MovieCompileType(val apiService: MovieApiService,
                       var typeList:String,
                       val apiMapperImpl: ApiMapper<List<MovieSearch>, MovieSearchDto>
) : PagingSource<Int, MovieSearch>() {
    override fun getRefreshKey(state: PagingState<Int, MovieSearch>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieSearch> {
        return try {


            Log.d("search","query "+typeList)

            val currentPage = params.key ?: 1

            val movieDto = apiService.getMovieByTypeList(typeList,currentPage)

            val movieSearch = apiMapperImpl.mapToDomain(movieDto)

            Log.d("ssss",movieDto.toString())

            LoadResult.Page(
                data = movieSearch,
                prevKey = if (currentPage == 1) null else currentPage - 1,
                nextKey = if (movieSearch.isEmpty()) null else currentPage + 1
            )
        }catch (e:Exception){
            Log.d("ssss",e.message.toString())

            LoadResult.Error(e)
        }
    }

}