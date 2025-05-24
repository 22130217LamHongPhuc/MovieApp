package com.example.movie.ui.search

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.movie.ui.movie.CardMovieSearch

@Composable
// 8.1.9. chuyển sang màn hình MovieSearchScreen
fun MovieSearchScreen(query:String,
                      viewModel:MovieSearchViewModel = hiltViewModel(), // 8.1.10. Khởi tạo MovieSearchModel
navController: NavController){
val moviePagingItems = viewModel.searchMoviePagingFlow.collectAsLazyPagingItems()
LaunchedEffect(Unit){
    Log.d("search",query+"Unit")
    // 8.1.11. gọi hàm trong viewmodel để setquery tìm kiếm
    viewModel.setQuery(query)
}

Box(modifier = Modifier.fillMaxSize().background(color = Color.Black)){
        if(moviePagingItems.itemCount==0){
CircularProgressIndicator(
        modifier = Modifier.size(30.dp),
strokeWidth = 3.dp,
color = Color.Red
          )
                  }else{
// 8.1.18. hien thi danh sách phim từ api cho người dùng
LazyColumn (modifier = Modifier
        .fillMaxSize()
              .padding(10.dp),
verticalArrangement = Arrangement.spacedBy(15.dp)){
        Log.d("search size",moviePagingItems.itemCount.toString())
item{
    Text(text = "Tìm kiếm cho : $query", style = MaterialTheme.typography.titleMedium.copy(color = Color.White, fontWeight = FontWeight.Bold))
}

items(moviePagingItems.itemCount,key = {
    index ->  moviePagingItems[index]!!.id
}) {
index -> moviePagingItems[index]?.let {
    Log.d("search","index $index")
    CardMovieSearch(movie = it, navController = navController)
}

              }

                      }
                      }
                      }


                      }

