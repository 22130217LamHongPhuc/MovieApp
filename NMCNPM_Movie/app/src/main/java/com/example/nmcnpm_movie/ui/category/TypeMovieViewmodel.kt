package com.example.movie.ui.category

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.movie.movie.domain.model.MovieSearch
import com.example.movie.ui.repository.MovieTypeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class TypeMovieViewmodel @Inject constructor(val repository: MovieTypeRepository) :ViewModel() {



    private val _type = MutableStateFlow("")
    private val _slug = MutableStateFlow("")

    val searchMoviePagingFlow = _slug.flatMapLatest { query ->
        Log.d("search",query+"1")
        if (query.isBlank()) {
            flowOf(PagingData.empty())
        } else {
            if (_type.value.equals("category"))
                repository.getMoviesCategorySearchPager(query).cachedIn(viewModelScope) // ✅ Thêm dòng này

            else repository.getMoviesCountrySearchPager(query).cachedIn(viewModelScope) // ✅ Thêm dòng này
        }
    }
        .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())


    fun setQuery(type:String,slug:String){
        if(_slug.value!=slug){
            _slug.value =slug
            _type.value=type
        }
    }


}