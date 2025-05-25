package com.example.movie.movie.data.mapper_impl

import com.example.movie.common.ApiMapper
import com.example.movie.movie.data.remote.models.MovieSearchDto
import com.example.movie.movie.domain.model.MovieSearch

class MovieSearchApiMapperImpl : ApiMapper<List<MovieSearch>,MovieSearchDto>{

    override fun mapToDomain(apiDto: MovieSearchDto): List<MovieSearch> {
        return apiDto.data?.items?.map {
                item ->  MovieSearch(
            formatEmptyValue(item?.id),
            formatEmptyValue(item?.name),
            formatEmptyValue(item?.slug),
            formatEmptyValue(item?.thumbUrl),
            formatEmptyValue(item?.episodeCurrent),
            formatEmptyValue(item?.posterUrl),
            item?.year ?: 0,
            formatEmptyValue(item?.country?.get(0)?.name),
            formatEmptyValue(item?.category?.get(0)?.name)
        )

        }  ?: listOf<MovieSearch>();
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
