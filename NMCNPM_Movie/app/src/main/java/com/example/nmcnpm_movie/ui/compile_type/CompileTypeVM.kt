package com.example.movie.ui.compile_type

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.movie.movie.domain.model.MovieSearch
import com.example.movie.ui.detail.UiState
import com.example.movie.backend.movie_service.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class CompileTypeVM @Inject constructor(val repository: MovieRepository) : ViewModel() {
    private var _state = MutableStateFlow<UiState<List<MovieSearch>>>(UiState.Default)
    var state = _state.asStateFlow()

    private var _slug = MutableStateFlow("")
    var slug = _slug.asStateFlow()



    val searchMoviePagingFlow = _slug.flatMapLatest { query ->
        Log.d("search",query+"1")
        if (_slug.value.isBlank()) {
            flowOf(PagingData.empty())
        } else {
            repository.fetchTypeListPaging(query,24).cachedIn(viewModelScope)
        }
    }
        .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())

    fun setSlug(slug: String) {
        Log.d("starrr", "$slug")

        if(_slug.value != slug) {
            Log.d("starrr", "$slug"+"yess")
            _slug.value = slug
        }
    }

}