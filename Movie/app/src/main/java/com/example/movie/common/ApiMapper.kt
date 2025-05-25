package com.example.movie.common

interface ApiMapper<Domain,Entity> {
    fun mapToDomain(apiDto: Entity) :Domain
}