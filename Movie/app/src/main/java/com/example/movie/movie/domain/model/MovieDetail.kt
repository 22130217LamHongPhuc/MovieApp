package com.example.movie.movie.domain.model

import com.example.movie.movie.data.remote.models.Category
import com.example.movie.movie.data.remote.models.Country
import com.example.movie.movie.data.remote.models.Episode

data class MovieDetail(val id:String, val name:String,
                       val slug:String, val thumbUrl:String,
                       val episodeCurrent:String, val poster:String,
                       val timeMovie:String, val episode_total:String,
                       val yearProduct:Int, val view:Int, val actors:List<String>,
                       val director:List<String>, val categorys:List<Category>,
                       val episodes:List<Episode>,val description:String,val country : List<Country>)


