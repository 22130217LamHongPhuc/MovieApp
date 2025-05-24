package com.example.nmcnpm_movie.ui.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.movie.movie.domain.model.MovieSearch
import com.example.movie.ui.repository.MovieSearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import java.util.concurrent.Flow
import javax.inject.Inject


@HiltViewModel
class MovieSearchViewModel @Inject constructor(val repository: MovieSearchRepository) : ViewModel() {
    private val _searchQuery = MutableStateFlow("")
    val searchMoviePagingFlow = _searchQuery
        .flatMapLatest { query ->
            Log.d("search", query + "1")
            // 8.1.13. thực hiện gọi hàm getMoviesSearchPager trong MovieSearchRepository
            repository.getMoviesSearchPager(query).cachedIn(viewModelScope)
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())

    //  8.1.17. nhận dữ liệu trả về từ MovieSearchRepository, thực hiện gán lại gia trị cho searchMoviePagingFlow
    fun setQuery(query: String) {
        Log.d("searcc", query + "start")
        if (_searchQuery.value != query) {
            // 8.1.12. thực hiện gán query cho _searchQuery
            _searchQuery.value = query
            Log.d("searcc", query)
        }
    }
}