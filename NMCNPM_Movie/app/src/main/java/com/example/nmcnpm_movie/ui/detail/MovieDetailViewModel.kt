package com.example.movie.ui.detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movie.movie.domain.model.Comment
import com.example.movie.ui.repository.MovieDetailRepository
import com.example.movie.movie.domain.model.MovieDetail
import com.example.movie.ui.repository.MovieDetailMoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
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


    private val _cmtList = MutableStateFlow<MutableList<Comment>>(mutableListOf())
    val cmtList: StateFlow<List<Comment>> = _cmtList.asStateFlow()

    private var _favoriteMovieDetail = MutableStateFlow<Boolean>(false)

    var favoriteMovieDetail = _favoriteMovieDetail.asStateFlow()


    fun getMovieDetailBySlug(slug:String,idMovie: String,idUser: String?){

        viewModelScope.launch {
            // 4.1.13 hệ thống chuyển _cmtMovieDetail sang  trạng thái  đang loading

            _stateMovieDetail.value =  UiState.Loading

            launch {
                // 4.1.15 Hệ thống thực hiện gọi fetchMovieDetailBySlug(slug) trong MovieDetailRepository

                val movieDetail = repository.fetchMovieDetailBySlug(slug)

                // 4.1.17 Hệ thống nhận dữ liệu thông tin chi tiết phim từ Api trả về

                if(movieDetail !=null){
                    // 4.1.18 Hệ thống cập nhập thông tin phim chi tiết  từ data từ api trả về cho _stateMovieDetail
                    Log.d("detail","ss")
                    _stateMovieDetail.value = UiState.Success(data = movieDetail )
                }else{
                    Log.d("detail","se")
                    _stateMovieDetail.value = UiState.Error(message = "Error")
                }
            }

            launch {
                if(!idUser.isNullOrBlank()) {
                    // 4.1.20 Hệ thống thực hiện gọi isFavoriteMovie(idUser,idMovie) trong MovieDetailRepository để lấy thông tin yêuu thích của bộ phim

                    val result = repository_movieDetailMore.isFavoriteMovie(idUser,idMovie)
                    // 4.1.22 Hệ thống nhận dữ liệu thông tin yêu thích Api trả về của Firebase

                    // 4.1.23 Hệ thống cập nhập thông tin yêu thích phim  từ data từ Firebase trả về cho _favoriteMovieDetail
                    _favoriteMovieDetail.value = result
                }
            }





        }
    }


    fun addComment(idMovie:String,idUser:String,content:String,nameUser:String){


        val cmt = Comment(idMovie,idUser, nameUser,content)

        // 4.1.38 Hệ thống thực hiện gọi addComment(cmt) trong MovieDetailMoreRepository để lưu bình luận của bộ phim

        repository_movieDetailMore.addComment(cmt){
                result ->
            // 4.1.40 Hệ thống nhận kết quả lưu trữ bình luận của Api trả về của Firebase

            // 4.1.41 Hệ thống cập nhật danh sách bình luận bao gôm bình luận mới nhất cho _cmtList

            result.onSuccess {
                _cmtList.update {
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

        // 4.1.28 Hệ thống thực hiện gọi getCommentsRealtime(idMovie) trong MovieDetailMoreRepository

        repository_movieDetailMore.getCommentsRealtime(idMovie){
            // 4.1.30 Hệ thống nhận dữ liệu danh sách bình luận Api trả về của Firebase
                list ->
            // 4.1.31 Hệ thống cập nhập thông tin danh sách bình luận từ data từ Firebase trả về cho _cmtList
            _cmtList.update { current ->

                (current + list) as MutableList<Comment>
            }

            Log.d("loadcmt",_cmtList.value.size.toString())

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