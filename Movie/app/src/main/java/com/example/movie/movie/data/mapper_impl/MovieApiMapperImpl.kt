package com.example.movie.movie.data.mapper_impl

import com.example.movie.common.ApiMapper
import com.example.movie.movie.data.remote.models.MovieDto
import com.example.movie.movie.domain.model.Movie

class MovieApiMapperImpl () : ApiMapper<List<Movie>, MovieDto> {

    override fun mapToDomain(apiDto: MovieDto): List<Movie> {
        return apiDto.items?.map { item ->  Movie(
            formatEmptyValue(item?.id),
            formatEmptyValue(item?.name),
            formatEmptyValue(item?.slug),
            formatEmptyValue(item?.thumbUrl),
            formatEmptyValue(item?.episodeCurrent),
            formatEmptyValue(item?.posterUrl))
        }  ?: listOf<Movie>();
    }

    fun formatEmptyValue(value:String?,default:String=""):String{
        if(value.isNullOrEmpty()) return "Unknown $default"

        return value
    }

}