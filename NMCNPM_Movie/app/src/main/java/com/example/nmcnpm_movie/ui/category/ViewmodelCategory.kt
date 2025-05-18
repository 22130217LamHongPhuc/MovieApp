package com.example.movie.ui.category

import androidx.lifecycle.ViewModel
import com.example.movie.movie.data.remote.models.Category
import com.example.movie.movie.data.remote.models.Country
import com.example.movie.movie.domain.model.Movie
import com.example.movie.movie.domain.model.MovieSearch
import com.example.movie.ui.home.HomeUiState
import com.example.movie.util.K
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ViewmodelCategory @Inject constructor():ViewModel() {
    private var _stateHome = MutableStateFlow(CategoryUiState())
    var stateHome = _stateHome.asStateFlow()
    init{
        _stateHome.update {
            it.copy(history = K.history, categories = K.categories, countries = K.countries )
        }
    }

}
data class CategoryUiState(
    val history: List<String> = emptyList(),
    val categories: List<Category> = emptyList(),
    val countries: List<Country> = emptyList(),
    val errorMessage: String? = null
)
