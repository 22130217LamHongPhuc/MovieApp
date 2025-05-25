package com.example.movie.movie.data.mapper_impl

import com.example.movie.common.ApiMapper
import com.example.movie.movie.data.remote.models.MovieDetailDto
import com.example.movie.movie.domain.model.Movie
import com.example.movie.movie.domain.model.MovieDetail

class MovieDetailApiMapperImpl : ApiMapper<MovieDetail,MovieDetailDto>{

    override fun mapToDomain(apiDto: MovieDetailDto): MovieDetail {
       return MovieDetail(
           id = formatEmptyValue(apiDto.movie?.id),
           name =formatEmptyValue(apiDto.movie?.name),
           slug = formatEmptyValue(apiDto.movie?.slug),
           thumbUrl = formatEmptyValue(apiDto.movie?.thumbUrl),
           episodeCurrent = formatEmptyValue(apiDto.movie?.episodeCurrent),
           poster = formatEmptyValue(apiDto.movie?.posterUrl),
           timeMovie = formatEmptyValue(apiDto.movie?.time),
           episode_total = formatEmptyValue(apiDto.movie?.episodeTotal),
           yearProduct = apiDto.movie?.year ?: 0,
           view =apiDto.movie?.view ?: 0,
           actors = apiDto.movie?.actor ?: emptyList(),
           director = apiDto.movie?.director ?: emptyList<String>(),
           categorys = apiDto.movie?.category ?: emptyList(),
           episodes = apiDto.episodes ?: emptyList(),
           description =  formatEmptyValue(apiDto.movie?.content),
           country = apiDto.movie?.country ?: emptyList()
       )
    }

    fun formatEmptyValue(value:String?,default:String=""):String{
        if(value.isNullOrEmpty()) return "Unknown $default"

        return value
    }

    fun formatEmptyListValue(value:List<Any>?,default:String=""):List<Any>? {
        if(value.isNullOrEmpty()) return emptyList()

        return value
    }




}
