package com.example.movie.ui.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movie.movie.domain.model.Movie
import com.example.movie.backend.movie_service.MovieHistoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MovieHistoryViewmodel @Inject constructor(val repository: MovieHistoryRepository): ViewModel(){

    var history = repository.getHistory().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    fun save(item: Movie) = viewModelScope.launch {
        repository.insert(item)
    }

    fun delete(slug: String) = viewModelScope.launch {
        repository.delete(slug)
    }

    fun clearAll() = viewModelScope.launch {
        repository.clear()
    }
}