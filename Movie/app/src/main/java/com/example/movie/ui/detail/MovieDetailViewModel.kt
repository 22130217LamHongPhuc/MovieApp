package com.example.movie.ui.detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movie.movie.domain.model.Comment
import com.example.movie.backend.movie_service.MovieDetailRepository
import com.example.movie.movie.domain.model.MovieDetail
import com.example.movie.backend.firebase_service.MovieDetailMoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MovieDetailViewModel @Inject constructor(val repository: MovieDetailRepository, val repository_movieDetailMore:MovieDetailMoreRepository) :ViewModel() {

    private var _stateMovieDetail = MutableStateFlow<UiState<MovieDetail>>(UiState.Default)

    var stateMovieDetail = _stateMovieDetail.asStateFlow()

    private var _cmtMovieDetail = MutableStateFlow<UiState<Boolean>>(UiState.Default)

    var cmtMovieDetail = _cmtMovieDetail.asStateFlow()


    private val _cmtStateList = MutableStateFlow<MutableList<Comment>>(mutableListOf())
    val cmtList: StateFlow<List<Comment>> = _cmtStateList.asStateFlow()

    private var _favoriteMovieDetail = MutableStateFlow<Boolean>(false)

    var favoriteMovieDetail = _favoriteMovieDetail.asStateFlow()


   fun getMovieDetailBySlug(slug:String,idMovie: String,idUser: String?){

        viewModelScope.launch {
            // 4.1.3 MovieDetailViewModel chuyển _stateMovieDetail sang trạng thái sang đang loading

            _stateMovieDetail.value =  UiState.Loading

          launch {
              // 4.1.5 MovieDetailViewModel thực hiện gọi fetchMovieDetailBySlug(slug) trong MovieDetailRepository
              val movieDeferrd = async {
                  repository.fetchMovieDetailBySlug(slug)
              }

              // 4.1.8 MovieDetailViewModel nhận dữ liệu thông tin chi tiết phim trả về MovieDetailRepository

              val movieDetail = movieDeferrd.await()


              if(movieDetail !=null){
                  // 4.1.9 MovieDetailViewModel cập nhập thông tin phim chi tiết từ MovieDetailRepository trả về cho _stateMovieDetail
                  Log.d("detail","ss")
                  _stateMovieDetail.value = UiState.Success(data = movieDetail )
              }

            }









            launch {
                if(!idUser.isNullOrBlank()) {

                    val result = repository_movieDetailMore.isFavoriteMovie(idUser,idMovie)

                    _favoriteMovieDetail.value = result
                }
            }





        }
    }


     fun addComment(idMovie:String,idUser:String,content:String,nameUser:String){


        val cmt = Comment(idMovie,idUser, nameUser,content)

         // 4.1.25 MovieDetailViewModel thực hiện gọi addComment(cmt) trong MovieDetailMoreRepository để lưu bình luận của bộ phim

         repository_movieDetailMore.addComment(cmt){
             // 4.1.28 MovieDetailViewModel nhận kết quả lưu trữ bình luận từ MovieDetailMoreRepository
             result ->


             result.onSuccess {

              // 4.1.29 MovieDetailViewModel cập nhật danh sách bình luận bao gôm bình luận mới nhất cho _cmtStateList
                 _cmtStateList.update {
                current ->
                    (current + cmt) as MutableList<Comment>
                }

           }

            result.onFailure {

            }

        }
    }

    fun loadAllComment(idMovie: String){
        Log.d("loadcmt","ss")

        // 4.1.14 MovieDetailViewModel thực hiện gọi getCommentsRealtime(idMovie) trong MovieDetailMoreRepository

        repository_movieDetailMore.getCommentsRealtime(idMovie){
            // 4.1.17 MovieDetailViewModel nhận dữ liệu danh sách bình luận từ MovieDetailMoreRepository
            list ->
            // 4.1.18 MovieDetailViewModel cập nhập thông tin danh sách bình luận từ MovieDetailMoreRepository trả về cho _cmtStateList
            _cmtStateList.update { current ->

                (current + list) as MutableList<Comment>
            }

            Log.d("loadcmt",_cmtStateList.value.size.toString())

        }
        }

    fun clickFavorite(uid: String?, id: String) {
        repository_movieDetailMore.setFavoriteMovie(uid,id){
            _favoriteMovieDetail.value = it
        }
    }

}




sealed class UiState<out T> {

    object Default : UiState<Nothing>()
    object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val message: String) : UiState<Nothing>()

}