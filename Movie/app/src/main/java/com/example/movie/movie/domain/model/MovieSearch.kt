package com.example.movie.movie.domain.model

import com.example.movie.movie.data.remote.models.Country

data class MovieSearch (val id:String,val name:String,val slug:String,val thumbUrl:String,
                        val episodeCurrent:String,val poster:String,val year:Int,val country: String,
                        val category: String)