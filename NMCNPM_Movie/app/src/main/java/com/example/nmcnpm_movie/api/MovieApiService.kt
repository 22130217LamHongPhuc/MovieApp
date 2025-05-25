package com.example.movie.movie.data.remote.api

import com.example.movie.movie.data.remote.models.MovieDetailDto
import com.example.movie.movie.data.remote.models.MovieDto
import com.example.movie.movie.data.remote.models.MovieSearchDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiService {





    @GET("danh-sach/phim-moi-cap-nhat-v3")
    suspend fun getMovieDataMovie(@Query("page") page:Int): MovieDto


    @GET("phim/{slug}")
    suspend fun getMovieBySlug(@Path("slug") slug:String): MovieDetailDto


    @GET("v1/api/tim-kiem")
    suspend fun searchMovie(@Query("keyword") keyword:String,@Query("page") page: Int): MovieSearchDto


    @GET("v1/api/danh-sach/{typeList}")
    suspend fun getMovieByTypeList(@Path("typeList") typeList:String, @Query("page") page: Int): MovieSearchDto


    @GET("v1/api/the-loai/{typeList}")
    suspend fun getMovieTypeCategory(@Path("typeList") typeList:String,@Query("page") page: Int): MovieSearchDto

    @GET("v1/api/quoc-gia/{typeList}")
    suspend fun getMovieTypeCountry(@Path("typeList") typeList:String,@Query("page") page: Int): MovieSearchDto

}