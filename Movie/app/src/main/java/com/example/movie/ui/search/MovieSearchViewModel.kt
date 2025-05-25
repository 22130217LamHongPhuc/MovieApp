package com.example.movie.ui.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.movie.backend.movie_service.MovieSearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


@HiltViewModel
class MovieSearchViewModel @Inject constructor(val repository: MovieSearchRepository) : ViewModel() {



    private val _searchQuery = MutableStateFlow("")

    val searchMoviePagingFlow = _searchQuery

        .flatMapLatest { query ->
            // 1.8 thuc hien  goi repository
            Log.d("search",query+"1")
            if (query.isBlank()) {
                flowOf(PagingData.empty())
            } else {
                repository.getMoviesSearchPager(query).cachedIn(viewModelScope)
            }
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())

    // 1.10 nhan data tu api tra ve

    fun setQuery(query: String) {
        Log.d("searcc",query+"start")
        if(_searchQuery.value != query){
            _searchQuery.value = query
            Log.d("searcc",query)
        }
    }


}