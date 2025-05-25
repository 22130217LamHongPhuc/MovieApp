package com.example.movie.di

import android.content.Context
import androidx.room.Room
import com.example.movie.common.ApiMapper
import com.example.movie.dao.AppDatabase
import com.example.movie.dao.MovieDAO
import com.example.movie.movie.data.mapper_impl.MovieApiMapperImpl
import com.example.movie.movie.data.mapper_impl.MovieDetailApiMapperImpl
import com.example.movie.movie.data.mapper_impl.MovieSearchApiMapperImpl
import com.example.movie.movie.data.remote.api.MovieApiService
import com.example.movie.movie.data.remote.models.MovieDetailDto
import com.example.movie.movie.data.remote.models.MovieDto
import com.example.movie.movie.data.remote.models.MovieSearchDto
import com.example.movie.backend.movie_service.MovieDetailRepository
import com.example.movie.backend.movie_service.MovieRepository
import com.example.movie.movie.domain.model.Movie
import com.example.movie.movie.domain.model.MovieDetail
import com.example.movie.movie.domain.model.MovieSearch
import com.example.movie.backend.firebase_service.MovieDetailMoreRepository
import com.example.movie.backend.firebase_service.EmailAuthRepo
import com.example.movie.backend.firebase_service.FacebookAuthHelper
import com.example.movie.backend.movie_service.MovieHistoryRepository
import com.example.movie.backend.movie_service.MovieSearchRepository
import com.example.movie.backend.movie_service.MovieTypeRepository
import com.example.movie.util.K
import com.google.firebase.auth.FirebaseAuth
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object MovieModule {

    val json  = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }



    @Provides
    @Singleton

    fun provideApiMapper():ApiMapper<List<Movie>, MovieDto> = MovieApiMapperImpl()

    @Provides
    @Singleton

    fun provideMovieDetailApiMapper():ApiMapper<MovieDetail, MovieDetailDto> = MovieDetailApiMapperImpl()


    @Provides
    @Singleton

    fun provideMovieSearchApiMapper():ApiMapper<List<MovieSearch>, MovieSearchDto> =
        MovieSearchApiMapperImpl()


    @Provides
    @Singleton

    fun provideApiService(): MovieApiService {
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .baseUrl(K.baseUrl)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
            .create(MovieApiService::class.java)
    }

    @Provides
    @Singleton

    fun provideMovieRepository( apiService: MovieApiService,apiMapper: ApiMapper<List<Movie>,MovieDto>,apiMapperCate: ApiMapper<List<MovieSearch>,MovieSearchDto>)
    : MovieRepository = MovieRepository(apiMapper,apiService,apiMapperCate)


    @Provides
    @Singleton

    fun provideMovieDetailRepository( apiService: MovieApiService,apiMapper: ApiMapper<MovieDetail,MovieDetailDto>)
            : MovieDetailRepository = MovieDetailRepository(apiService,apiMapper)



    @Provides
    @Singleton

    fun provideMovieSearchRepository( apiService: MovieApiService,apiMapper: ApiMapper<List<MovieSearch>,MovieSearchDto>)
            : MovieSearchRepository = MovieSearchRepository(apiService,apiMapper)


    @Provides
    @Singleton

    fun provideMovieTypeSearchRepository( apiService: MovieApiService,apiMapper: ApiMapper<List<MovieSearch>,MovieSearchDto>)
            : MovieTypeRepository = MovieTypeRepository(apiService,apiMapper)



    @Provides
    @Singleton
    fun provideFirebaseAuth():FirebaseAuth = FirebaseAuth.getInstance()





    @Provides
    @Singleton
    fun provideEmailAuthRepo( auth: FirebaseAuth,@ApplicationContext context: Context): EmailAuthRepo = EmailAuthRepo(auth,context)

    @Provides
    @Singleton
    fun provideFbAuthRepo( auth: FirebaseAuth): FacebookAuthHelper = FacebookAuthHelper(auth)



    @Provides
    @Singleton
    fun provideCommentRepository () :MovieDetailMoreRepository = MovieDetailMoreRepository()


    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext app: Context): AppDatabase =
        Room.databaseBuilder(app, AppDatabase::class.java, "movie_db").build()

    @Provides
    fun provideReadingHistoryDao(db: AppDatabase): MovieDAO = db.MovieHistoryDao()


    @Provides
    @Singleton
    fun provideMovieHistoryRepository (dao: MovieDAO) :MovieHistoryRepository = MovieHistoryRepository(dao)
}